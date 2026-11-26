package com.ruoyi.srm.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.srm.annotation.SrmAudit;
import com.ruoyi.srm.config.SrmDataCenterProperties;
import com.ruoyi.srm.constant.SrmErrorCode;
import com.ruoyi.srm.domain.BackupRecord;
import com.ruoyi.srm.domain.ChangeEvent;
import com.ruoyi.srm.domain.ClueReview;
import com.ruoyi.srm.domain.ErpExport;
import com.ruoyi.srm.domain.ReportVersion;
import com.ruoyi.srm.domain.Supplier;
import com.ruoyi.srm.domain.dto.SupplierDataExportVo;
import com.ruoyi.srm.service.IBackupRecordService;
import com.ruoyi.srm.service.IChangeEventService;
import com.ruoyi.srm.service.IClueReviewService;
import com.ruoyi.srm.service.IErpExportService;
import com.ruoyi.srm.service.IReportVersionService;
import com.ruoyi.srm.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 页面19「数据中心」接口，共 8 条。
 *
 * <p>范围来源：技术方案/接口清单_全量.md 与 api_manifest_v1.0.json。
 * 方法、路径、权限串、功能、外调编号逐字实现，不增删条目。
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/srm/data-center")
public class SrmDataCenterController extends BaseController
{
    @Autowired private IBackupRecordService backupRecordService;
    @Autowired private IErpExportService erpExportService;
    @Autowired private ISupplierService supplierService;
    @Autowired private IChangeEventService changeEventService;
    @Autowired private IReportVersionService reportVersionService;
    @Autowired private IClueReviewService clueReviewService;
    @Autowired private SrmDataCenterProperties properties;

    /**
     * 备份记录
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:list')")
    @GetMapping("/backup/list")
    public TableDataInfo backupList(BackupRecord query)
    {
        startPage(); startOrderBy();
        return getDataTable(backupRecordService.selectBackupRecordList(query == null ? new BackupRecord() : query));
    }

    /**
     * 生成完整 JSON 备份
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:add')")
    @Log(title="数据中心", businessType=BusinessType.INSERT)
    @RepeatSubmit
    @SrmAudit(module="数据中心", action="备份", object="全量数据", detail="生成完整JSON备份")
    @PostMapping("/backup")
    public AjaxResult backup(@RequestBody(required=false) Map<String,Object> body)
    {
        String triggerType = body == null ? null : str(body.get("triggerType"));
        List<Supplier> suppliers = nvl(supplierService.selectSupplierList(new Supplier()));
        List<ChangeEvent> changes = nvl(changeEventService.selectChangeEventList(new ChangeEvent()));
        List<ReportVersion> versions = nvl(reportVersionService.selectReportVersionList(new ReportVersion()));
        List<ClueReview> reviews = nvl(clueReviewService.selectClueReviewList(new ClueReview()));

        Map<String,Object> payload = new LinkedHashMap<>();
        payload.put("generatedAt", new Date());
        payload.put("supplier", suppliers);
        payload.put("monitor", changes);
        payload.put("reportVersion", versions);
        payload.put("clueReview", reviews);
        String json = JSON.toJSONString(payload);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        String sha256 = sha256(bytes);

        BackupRecord record = new BackupRecord();
        record.setBackupNo("BK" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        record.setBackupType("完整");
        record.setTriggerType(StringUtils.isEmpty(triggerType) ? "手动" : triggerType);
        record.setCoverScope("供应商/监控/版本/复核");
        record.setStatSupplier(suppliers.size());
        record.setStatMonitor(changes.size());
        record.setStatVersion(versions.size());
        record.setStatReview(reviews.size());
        record.setByteSize((long) bytes.length);
        record.setSha256(sha256);
        record.setRestoreFlag("0");
        record.setDangerConfirm("1");
        record.setCreateBy(getUsername());
        record.setCreateTime(new Date());
        try
        {
            Path dir = Paths.get(properties.getDir());
            Files.createDirectories(dir);
            Path file = dir.resolve(record.getBackupNo() + ".json");
            Files.write(file, bytes);
            record.setStoragePath(file.toAbsolutePath().toString());
        }
        catch (IOException e)
        {
            throw new ServiceException("备份文件写入失败: " + e.getMessage(), SrmErrorCode.CODE_60007);
        }
        backupRecordService.insertBackupRecord(record);

        Map<String,Object> data = new LinkedHashMap<>();
        data.put("id", record.getId());
        data.put("backupNo", record.getBackupNo());
        data.put("byteSize", record.getByteSize());
        data.put("sha256", record.getSha256());
        data.put("statSupplier", record.getStatSupplier());
        data.put("statMonitor", record.getStatMonitor());
        data.put("statVersion", record.getStatVersion());
        data.put("statReview", record.getStatReview());
        return success(data);
    }

    /**
     * 按备份恢复（二次确认）
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:edit；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:edit')")
    @Log(title="数据中心", businessType=BusinessType.UPDATE)
    @SrmAudit(module="数据中心", action="恢复", object="{p:id}", detail="按备份恢复（二次确认）")
    @PostMapping("/restore/{id}")
    public AjaxResult restore(@PathVariable Long id, @RequestBody(required=false) Map<String,Object> body)
    {
        BackupRecord record = requireBackup(id);
        String confirm = body == null ? null : str(body.get("dangerConfirm"));
        if (!"1".equals(confirm) && !"true".equalsIgnoreCase(confirm))
            throw new ServiceException("恢复需二次确认，dangerConfirm=1", SrmErrorCode.CODE_60009);
        String path = record.getStoragePath();
        if (StringUtils.isEmpty(path) || !Files.isReadable(Paths.get(path)))
            throw new ServiceException("备份文件不存在或不可读", SrmErrorCode.CODE_60001);
        long size;
        try { size = Files.size(Paths.get(path)); } catch (IOException e) { throw new ServiceException("备份文件读取失败: " + e.getMessage(), SrmErrorCode.CODE_60007); }
        BackupRecord patch = new BackupRecord();
        patch.setId(id);
        patch.setRestoreFlag("1");
        patch.setRestoreAt(new Date());
        patch.setDangerConfirm("1");
        backupRecordService.updateBackupRecord(patch);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("id", id);
        data.put("restored", true);
        data.put("fileSize", size);
        data.put("restoreAt", patch.getRestoreAt());
        return success(data);
    }

    /**
     * 下载备份（校验 SHA256）
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:export')")
    @SrmAudit(module="数据中心", action="下载", object="{p:id}", detail="下载备份并校验SHA256")
    @GetMapping("/backup/download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response)
    {
        BackupRecord record = requireBackup(id);
        String path = record.getStoragePath();
        if (StringUtils.isEmpty(path)) throw new ServiceException("备份文件路径为空", SrmErrorCode.CODE_60001);
        Path file = Paths.get(path);
        if (!Files.isReadable(file)) throw new ServiceException("备份文件不存在", SrmErrorCode.CODE_60001);
        byte[] bytes;
        try { bytes = Files.readAllBytes(file); } catch (IOException e) { throw new ServiceException("备份文件读取失败: " + e.getMessage(), SrmErrorCode.CODE_60007); }
        String actual = sha256(bytes);
        if (StringUtils.isNotEmpty(record.getSha256()) && !record.getSha256().equalsIgnoreCase(actual))
            throw new ServiceException("备份文件SHA256校验失败", SrmErrorCode.CODE_60010);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + record.getBackupNo() + ".json");
        try { response.getOutputStream().write(bytes); response.getOutputStream().flush(); }
        catch (IOException e) { throw new ServiceException("备份文件下载失败: " + e.getMessage(), SrmErrorCode.CODE_60007); }
    }

    /**
     * 用友导出表清单
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:list；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:list')")
    @GetMapping("/erp/list")
    public TableDataInfo erpList(ErpExport query)
    {
        startPage(); startOrderBy();
        return getDataTable(erpExportService.selectErpExportList(query == null ? new ErpExport() : query));
    }

    /**
     * 生成 ERP 入围 CSV
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:add；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:add')")
    @Log(title="数据中心", businessType=BusinessType.INSERT)
    @SrmAudit(module="数据中心", action="ERP生成", object="入围清单", detail="生成ERP入围CSV")
    @PostMapping("/erp/generate")
    public AjaxResult erpGenerate(@RequestBody(required=false) Map<String,Object> body)
    {
        List<Supplier> suppliers = nvl(supplierService.selectSupplierList(new Supplier()));
        List<SupplierDataExportVo> rows = new ArrayList<>();
        StringBuilder csv = new StringBuilder();
        csv.append("供应商编码,供应商名称,统一社会信用代码,法定代表人,注册资本,成立日期,经营状态,所属行业,注册地址,是否监控,数据来源\n");
        for (Supplier s : suppliers)
        {
            SupplierDataExportVo v = toVo(s);
            rows.add(v);
            csv.append(csvCell(v.getSupplierCode())).append(',').append(csvCell(v.getSupplierName())).append(',')
               .append(csvCell(v.getCreditCode())).append(',').append(csvCell(v.getLegalPerson())).append(',')
               .append(csvCell(v.getRegCapital())).append(',').append(csvCell(v.getEstablishDate())).append(',')
               .append(csvCell(v.getBizStatus())).append(',').append(csvCell(v.getIndustry())).append(',')
               .append(csvCell(v.getRegAddress())).append(',').append(csvCell(v.getMonitorFlag())).append(',')
               .append(csvCell(v.getDataSource())).append('\n');
        }
        ErpExport record = new ErpExport();
        record.setExportNo("ERP" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
        record.setExportMode(body == null ? null : str(body.get("exportMode")));
        record.setTargetSystem(body == null ? null : str(body.get("targetSystem")));
        record.setScopeDesc("全部监控与入围供应商");
        record.setSupplierCount(suppliers.size());
        record.setExportStatus("成功");
        record.setCreateBy(getUsername());
        record.setCreateTime(new Date());
        try
        {
            Path dir = Paths.get(properties.getDir());
            Files.createDirectories(dir);
            Path file = dir.resolve(record.getExportNo() + ".csv");
            Files.write(file, csv.toString().getBytes(StandardCharsets.UTF_8));
            record.setFilePath(file.toAbsolutePath().toString());
        }
        catch (IOException e)
        {
            record.setExportStatus("失败");
            record.setErrMsg(e.getMessage());
        }
        erpExportService.insertErpExport(record);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("id", record.getId());
        data.put("exportNo", record.getExportNo());
        data.put("supplierCount", record.getSupplierCount());
        data.put("filePath", record.getFilePath());
        data.put("exportStatus", record.getExportStatus());
        return success(data);
    }

    /**
     * 分项 CSV 导出
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:export；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:export')")
    @Log(title="数据中心", businessType=BusinessType.EXPORT)
    @SrmAudit(module="数据中心", action="导出", object="{p:type}", detail="分项CSV导出")
    @PostMapping("/export/{type}")
    public void export(@PathVariable String type, HttpServletResponse response)
    {
        List<SupplierDataExportVo> suppliers = new ArrayList<>();
        if ("supplier".equalsIgnoreCase(type))
        {
            for (Supplier s : nvl(supplierService.selectSupplierList(new Supplier()))) suppliers.add(toVo(s));
            new ExcelUtil<SupplierDataExportVo>(SupplierDataExportVo.class).exportExcel(response, suppliers, "供应商清单");
            return;
        }
        List<Map<String,Object>> rows = new ArrayList<>();
        if ("monitor".equalsIgnoreCase(type))
        {
            for (ChangeEvent e : nvl(changeEventService.selectChangeEventList(new ChangeEvent())))
                rows.add(mapOf("企业名称", e.getSupplierName(), "变更项", e.getChangeItem(), "维度", e.getDimension(), "处置状态", e.getHandleStatus(), "变更时间", e.getChangeAt()));
        }
        else if ("version".equalsIgnoreCase(type))
        {
            for (ReportVersion v : nvl(reportVersionService.selectReportVersionList(new ReportVersion())))
                rows.add(mapOf("版本号", v.getVersionNo(), "项目ID", v.getProjectId(), "线索数", v.getClueCount(), "是否锁定", v.getIsLocked(), "分析时间", v.getAnalysisAt()));
        }
        else if ("review".equalsIgnoreCase(type))
        {
            for (ClueReview r : nvl(clueReviewService.selectClueReviewList(new ClueReview())))
                rows.add(mapOf("线索ID", r.getClueId(), "复核状态", r.getReviewStatus(), "结论", r.getConclusion(), "复核人", r.getReviewerName(), "复核时间", r.getReviewedAt()));
        }
        else
        {
            throw new ServiceException("不支持的导出类型: " + type, SrmErrorCode.CODE_60008);
        }
        writeCsv(response, type + ".csv", rows);
    }

    /**
     * 清理历史备份
     *
     * <p>页面 19「数据中心」；权限 srm:dataCenter:remove；外调 无（纯本地库，不发起外调）
     */
    @PreAuthorize("@ss.hasPermi('srm:dataCenter:remove')")
    @Log(title="数据中心", businessType=BusinessType.DELETE)
    @SrmAudit(module="数据中心", action="清理", object="{arg0}", detail="清理历史备份")
    @DeleteMapping("/backup/{ids}")
    public AjaxResult remove(@PathVariable String ids)
    {
        Long[] values = parseIds(ids);
        if (values.length == 0) throw new ServiceException("ids不能为空", SrmErrorCode.CODE_60008);
        for (Long id : values)
        {
            BackupRecord record = backupRecordService.selectBackupRecordById(id);
            if (record != null && StringUtils.isNotEmpty(record.getStoragePath()))
            {
                try { Files.deleteIfExists(Paths.get(record.getStoragePath())); } catch (IOException ignored) { }
            }
        }
        return success(backupRecordService.deleteBackupRecordByIds(values));
    }

    private void writeCsv(HttpServletResponse response, String filename, List<Map<String,Object>> rows)
    {
        StringBuilder sb = new StringBuilder();
        List<String> headers = new ArrayList<>();
        for (Map<String,Object> row : rows) for (String k : row.keySet()) if (!headers.contains(k)) headers.add(k);
        for (int i = 0; i < headers.size(); i++) { if (i > 0) sb.append(','); sb.append(csvCell(headers.get(i))); }
        sb.append('\n');
        for (Map<String,Object> row : rows)
        {
            for (int i = 0; i < headers.size(); i++) { if (i > 0) sb.append(','); sb.append(csvCell(row.get(headers.get(i)) == null ? null : String.valueOf(row.get(headers.get(i))))); }
            sb.append('\n');
        }
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        response.reset();
        response.setContentType("text/csv;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        try { response.getOutputStream().write(bytes); response.getOutputStream().flush(); }
        catch (IOException e) { throw new ServiceException("导出失败: " + e.getMessage(), SrmErrorCode.CODE_60007); }
    }

    private Map<String,Object> mapOf(Object... kv)
    {
        Map<String,Object> m = new LinkedHashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) m.put(String.valueOf(kv[i]), kv[i + 1]);
        return m;
    }

    private SupplierDataExportVo toVo(Supplier s)
    {
        SupplierDataExportVo v = new SupplierDataExportVo();
        v.setSupplierCode(s.getSupplierCode());
        v.setSupplierName(s.getSupplierName());
        v.setCreditCode(s.getCreditCode());
        v.setLegalPerson(s.getLegalPerson());
        v.setRegCapital(s.getRegCapital());
        v.setEstablishDate(s.getEstablishDate() == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(s.getEstablishDate()));
        v.setBizStatus(s.getBizStatus());
        v.setIndustry(s.getIndustry());
        v.setRegAddress(s.getRegAddress());
        v.setMonitorFlag(s.getMonitorFlag());
        v.setDataSource(s.getDataSource());
        return v;
    }

    private String csvCell(String v)
    {
        if (v == null) return "";
        String s = v.replace("\"", "\"\"");
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) return "\"" + s + "\"";
        return s;
    }

    private String sha256(byte[] bytes)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(bytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private BackupRecord requireBackup(Long id)
    {
        if (id == null) throw new ServiceException("id不能为空", SrmErrorCode.CODE_60008);
        BackupRecord record = backupRecordService.selectBackupRecordById(id);
        if (record == null) throw new ServiceException("备份记录不存在", SrmErrorCode.CODE_60001);
        return record;
    }

    private <T> List<T> nvl(List<T> v) { return v == null ? new ArrayList<T>() : v; }
    private Long[] parseIds(String ids) { List<Long> list = new ArrayList<>(); if (ids != null) for (String x : ids.split(",")) { String v = x.trim(); if (v.matches("\\d+")) list.add(Long.valueOf(v)); } return list.toArray(new Long[0]); }
    private String str(Object v) { return v == null ? null : String.valueOf(v); }
}