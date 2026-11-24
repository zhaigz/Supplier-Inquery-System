package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ChangeEvent;
import com.ruoyi.srm.mapper.ChangeEventMapper;
import com.ruoyi.srm.service.IChangeEventService;

@Service
public class ChangeEventServiceImpl implements IChangeEventService
{
    @Autowired
    private ChangeEventMapper ChangeEventMapper;

    public ChangeEvent selectChangeEventById(Long id) { return ChangeEventMapper.selectChangeEventById(id); }
    public List<ChangeEvent> selectChangeEventList(ChangeEvent entity) { return ChangeEventMapper.selectChangeEventList(entity); }
    public int insertChangeEvent(ChangeEvent entity) { return ChangeEventMapper.insertChangeEvent(entity); }
    public int updateChangeEvent(ChangeEvent entity) { return ChangeEventMapper.updateChangeEvent(entity); }
    public int deleteChangeEventById(Long id) { return ChangeEventMapper.deleteChangeEventById(id); }
    public int deleteChangeEventByIds(Long[] ids) { return ChangeEventMapper.deleteChangeEventByIds(ids); }
}
