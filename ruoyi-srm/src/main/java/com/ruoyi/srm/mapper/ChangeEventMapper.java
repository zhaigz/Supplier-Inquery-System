package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ChangeEvent;

public interface ChangeEventMapper
{
    ChangeEvent selectChangeEventById(Long id);
    List<ChangeEvent> selectChangeEventList(ChangeEvent entity);
    int insertChangeEvent(ChangeEvent entity);
    int updateChangeEvent(ChangeEvent entity);
    int deleteChangeEventById(Long id);
    int deleteChangeEventByIds(Long[] ids);
}
