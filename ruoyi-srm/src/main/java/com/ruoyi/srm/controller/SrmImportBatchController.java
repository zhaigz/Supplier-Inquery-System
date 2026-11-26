package com.ruoyi.srm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.constant.SrmConstants;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.ImportBatch;
import com.ruoyi.srm.domain.ImportRow;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.ImportRowExportVo;
import com.ruoyi.srm.service.IImportBatchService;
import com.ruoyi.srm.service.IImportRowService;
import com.ruoyi.srm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面14「批量导入」接口，共 7 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/supplier-import")
public class SrmImportBatchController extends BaseController
{
    @Autowired private IImportBatchService importBatchService;
    @Autowired private IImportRowService importRowService;
    @Autowired private ISupplierService supplierService;

    /**
     * 解析+三类校验
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:add')")
    @Log(title="供应商导入", businessType=BusinessType.INSERT)
    @SrmAudit(module="批量导入", action="解析", object="导入内容", detail="解析并三类校验")
    @PostMapping("/parse")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult parse(@RequestBody Map<String,Object> body)
    {
        if (body == null) throw new ServiceException("请求体不能为空", SrmErrorCode.CODE_60008);
        String content = str(body.get("content"));
        String sourceType = nvl(str(body.get("sourceType")), "paste");
        String separator = nvl(str(body.get("separator")), ",");
        String hasHeader = nvl(str(body.get("hasHeader")), SrmConstants.YES);
        String dupRule = nvl(str(body.get("dupRule")), "creditCode");
        if (StringUtils.isEmpty(content)) throw new ServiceException("导入内容不能为空", SrmErrorCode.CODE_60008);
        if (!",".equals(separator) && !"\t".equals(separator)) throw new ServiceException("separator仅支持逗号或制表符", SrmErrorCode.CODE_60008);

        ImportBatch batch = new ImportBatch();
        batch.setBatchNo("IMP" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        batch.setSourceType(sourceType);
        batch.setSeparator(separator);
        batch.setHasHeader(SrmConstants.YES.equals(hasHeader) ? SrmConstants.YES : SrmConstants.NO);
        batch.setImportStatus("待确认");
        batch.setDupRule(dupRule);
        batch.setFilePath(str(body.get("filePath")));
        batch.setCreateBy(getUsername());
        batch.setCreateTime(new Date());
        batch.setTotalRows(0); batch.setOkRows(0); batch.setDupRows(0); batch.setInvalidRows(0);
        importBatchService.insertImportBatch(batch);

        String[] lines = content.replace("\r\n", "\n").replace("\r", "\n").split("\n");
        List<ImportRow> rows = new ArrayList<>();
        Map<String,Integer> seen = new LinkedHashMap<>();
        boolean headerConsumed = false;
        int no = 0;
        for (String line : lines)
        {
            if (StringUtils.isEmpty(line)) continue;
            no++;
            if (!headerConsumed && SrmConstants.YES.equals(batch.getHasHeader())) { headerConsumed = true; continue; }
            headerConsumed = true;
            String[] cells = line.split(java.util.regex.Pattern.quote(separator), -1);
            ImportRow row = new ImportRow();
            row.setBatchId(batch.getId());
            row.setRowNo(no);
            row.setSupplierName(trim(cells, 0));
            row.setCreditCode(trim(cells, 1));
            row.setLegalPerson(trim(cells, 2));
            String monitor = trim(cells, 3);
            row.setMonitorFlag(SrmConstants.YES.equals(monitor) ? SrmConstants.YES : SrmConstants.NO);
            row.setCreateTime(new Date());
            row.setCheckResult(validate(row, dupRule, seen, batch.getId()));
            if ("无效".equals(row.getCheckResult())) batch.setInvalidRows(batch.getInvalidRows() + 1);
            else if ("重复".equals(row.getCheckResult())) batch.setDupRows(batch.getDupRows() + 1);
            else batch.setOkRows(batch.getOkRows() + 1);
            rows.add(row);
        }
        batch.setTotalRows(rows.size());
        importBatchService.updateImportBatch(batch);
        for (ImportRow row : rows) importRowService.insertImportRow(row);

        Map<String,Object> data = new LinkedHashMap<>();
        data.put("batchId", batch.getId());
        data.put("batchNo", batch.getBatchNo());
        data.put("totalRows", batch.getTotalRows());
        data.put("okRows", batch.getOkRows());
        data.put("dupRows", batch.getDupRows());
        data.put("invalidRows", batch.getInvalidRows());
        data.put("preview", rows.size() > 50 ? rows.subList(0, 50) : rows);
        return success(data);
    }

    /**
     * 批次清单
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:list')")
    @GetMapping("/list")
    public TableDataInfo list(ImportBatch query)
    {
        startPage(); startOrderBy();
        return getDataTable(importBatchService.selectImportBatchList(query == null ? new ImportBatch() : query));
    }

    /**
     * 行级校验结果
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:list')")
    @GetMapping("/row/list")
    public TableDataInfo rowList(ImportRow query)
    {
        startPage(); startOrderBy();
        return getDataTable(importRowService.selectImportRowList(query == null ? new ImportRow() : query));
    }

    /**
     * 确认入库
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:add')")
    @Log(title="供应商导入", businessType=BusinessType.INSERT)
    @SrmAudit(module="批量导入", action="确认", object="{p:batchId}", detail="确认导入有效行")
    @PostMapping("/confirm/{batchId}")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult confirm(@PathVariable Long batchId)
    {
        ImportBatch batch = requireBatch(batchId);
        if (!"待确认".equals(batch.getImportStatus()) && !"部分导入".equals(batch.getImportStatus()))
            throw new ServiceException("当前批次状态不可导入: " + batch.getImportStatus(), SrmErrorCode.CODE_60009);
        ImportRow q = new ImportRow();
        q.setBatchId(batchId);
        List<ImportRow> rows = importRowService.selectImportRowList(q);
        int imported = 0;
        for (ImportRow row : rows == null ? new ArrayList<ImportRow>() : rows)
        {
            if (!"有效".equals(row.getCheckResult()) || row.getSupplierId() != null) continue;
            Supplier supplier = new Supplier();
            supplier.setSupplierName(row.getSupplierName());
            supplier.setCreditCode(row.getCreditCode());
            supplier.setLegalPerson(row.getLegalPerson());
            supplier.setMonitorFlag(row.getMonitorFlag());
            supplier.setDataSource(SrmConstants.DATA_SOURCE_IMPORT);
            supplier.setStatus(SrmConstants.YES);
            supplier.setDelFlag(SrmConstants.DEL_FLAG_EXIST);
            supplier.setDataVersion(1);
            supplier.setFirstImportBatch(batch.getBatchNo());
            supplier.setCreateBy(getUsername());
            supplier.setCreateTime(new Date());
            supplierService.insertSupplier(supplier);
            ImportRow patch = new ImportRow();
            patch.setId(row.getId());
            patch.setSupplierId(supplier.getId());
            importRowService.updateImportRow(patch);
            imported++;
        }
        ImportBatch update = new ImportBatch();
        update.setId(batchId);
        update.setImportStatus(imported > 0 ? "已导入" : "部分导入");
        update.setConfirmTime(new Date());
        importBatchService.updateImportBatch(update);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("batchId", batchId);
        data.put("imported", imported);
        data.put("importStatus", update.getImportStatus());
        return success(data);
    }

    /**
     * 单行修正/忽略
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:edit')")
    @Log(title="供应商导入", businessType=BusinessType.UPDATE)
    @SrmAudit(module="批量导入", action="修正", object="{p:row.id}", detail="单行修正或忽略")
    @PutMapping("/row")
    public AjaxResult rowEdit(@RequestBody ImportRow row)
    {
        if (row == null || row.getId() == null) throw new ServiceException("行id不能为空", SrmErrorCode.CODE_60008);
        ImportRow exist = importRowService.selectImportRowById(row.getId());
        if (exist == null) throw new ServiceException("导入行不存在", SrmErrorCode.CODE_60001);
        if ("忽略".equals(row.getCheckResult()) || "无效".equals(row.getCheckResult()) || "有效".equals(row.getCheckResult()))
        {
            ImportRow patch = new ImportRow();
            patch.setId(row.getId());
            patch.setCheckResult(row.getCheckResult());
            if ("有效".equals(row.getCheckResult())) patch.setErrMsg("");
            importRowService.updateImportRow(patch);
        }
        else
        {
            row.setBatchId(exist.getBatchId());
            importRowService.updateImportRow(row);
        }
        recalcBatch(exist.getBatchId());
        return success(importRowService.selectImportRowById(row.getId()));
    }

    /**
     * 导出错误行
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:export')")
    @Log(title="供应商导入", businessType=BusinessType.EXPORT)
    @SrmAudit(module="批量导入", action="导出", object="错误行", detail="导出错误行")
    @PostMapping("/exportError")
    public void exportError(@RequestBody(required=false) ImportRow query, HttpServletResponse response)
    {
        ImportRow q = query == null ? new ImportRow() : query;
        List<ImportRow> rows = importRowService.selectImportRowList(q);
        List<ImportRowExportVo> out = new ArrayList<>();
        for (ImportRow row : rows == null ? new ArrayList<ImportRow>() : rows)
        {
            if ("有效".equals(row.getCheckResult())) continue;
            ImportRowExportVo v = new ImportRowExportVo();
            v.setRowNo(row.getRowNo());
            v.setSupplierName(row.getSupplierName());
            v.setCreditCode(row.getCreditCode());
            v.setLegalPerson(row.getLegalPerson());
            v.setMonitorFlag(row.getMonitorFlag());
            v.setCheckResult(row.getCheckResult());
            v.setErrMsg(row.getErrMsg());
            out.add(v);
        }
        new ExcelUtil<ImportRowExportVo>(ImportRowExportVo.class).exportExcel(response, out, "导入错误行");
    }

    /**
     * 批次详情
     *
     * <p>页面 14「批量导入」；权限 srm:supplierImport:query；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:supplierImport:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        ImportBatch batch = requireBatch(id);
        ImportRow q = new ImportRow();
        q.setBatchId(id);
        List<ImportRow> rows = importRowService.selectImportRowList(q);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("batch", batch);
        data.put("rows", rows == null ? new ArrayList<ImportRow>() : rows);
        return success(data);
    }

    private String validate(ImportRow row, String dupRule, Map<String,Integer> seen, Long batchId)
    {
        List<String> errs = new ArrayList<>();
        if (StringUtils.isEmpty(row.getSupplierName())) errs.add("供应商名称为空");
        if (StringUtils.isEmpty(row.getCreditCode())) errs.add("统一社会信用代码为空");
        String key = "supplierName".equalsIgnoreCase(dupRule) ? row.getSupplierName() : row.getCreditCode();
        if (StringUtils.isNotEmpty(key))
        {
            if (seen.containsKey(key))
            {
                row.setErrMsg("同批次重复，首次出现行: " + seen.get(key));
                return "重复";
            }
            seen.put(key, row.getRowNo());
        }
        if (StringUtils.isNotEmpty(row.getCreditCode()))
        {
            Supplier q = new Supplier();
            q.setCreditCode(row.getCreditCode());
            List<Supplier> exists = supplierService.selectSupplierList(q);
            if (exists != null && !exists.isEmpty())
            {
                row.setDupOfSupplierId(exists.get(0).getId());
                row.setErrMsg("与库内供应商重复: " + exists.get(0).getSupplierName());
                return "重复";
            }
        }
        if (!errs.isEmpty())
        {
            row.setErrMsg(String.join("；", errs));
            return "无效";
        }
        return "有效";
    }

    private void recalcBatch(Long batchId)
    {
        if (batchId == null) return;
        ImportRow q = new ImportRow();
        q.setBatchId(batchId);
        List<ImportRow> rows = importRowService.selectImportRowList(q);
        int ok = 0, dup = 0, invalid = 0;
        for (ImportRow row : rows == null ? new ArrayList<ImportRow>() : rows)
        {
            if ("重复".equals(row.getCheckResult())) dup++;
            else if ("无效".equals(row.getCheckResult())) invalid++;
            else if (!"忽略".equals(row.getCheckResult())) ok++;
        }
        ImportBatch patch = new ImportBatch();
        patch.setId(batchId);
        patch.setTotalRows(rows == null ? 0 : rows.size());
        patch.setOkRows(ok);
        patch.setDupRows(dup);
        patch.setInvalidRows(invalid);
        importBatchService.updateImportBatch(patch);
    }

    private ImportBatch requireBatch(Long id)
    {
        if (id == null) throw new ServiceException("批次id不能为空", SrmErrorCode.CODE_60008);
        ImportBatch batch = importBatchService.selectImportBatchById(id);
        if (batch == null) throw new ServiceException("导入批次不存在", SrmErrorCode.CODE_60001);
        return batch;
    }

    private String trim(String[] cells, int index) { return cells.length > index ? StringUtils.trim(cells[index]) : null; }
    private String nvl(String v, String def) { return StringUtils.isEmpty(v) ? def : v; }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
}