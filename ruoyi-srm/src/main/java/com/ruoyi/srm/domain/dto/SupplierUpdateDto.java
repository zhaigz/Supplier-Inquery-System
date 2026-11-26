package com.ruoyi.srm.domain.dto;
import java.math.BigDecimal; import java.util.Date; import com.fasterxml.jackson.annotation.JsonFormat;
public class SupplierUpdateDto extends SupplierCreateDto { private Long id; public Long getId(){return id;} public void setId(Long v){id=v;} }
