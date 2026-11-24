package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.RelClue;
import com.ruoyi.srm.mapper.RelClueMapper;
import com.ruoyi.srm.service.IRelClueService;

@Service
public class RelClueServiceImpl implements IRelClueService
{
    @Autowired
    private RelClueMapper RelClueMapper;

    public RelClue selectRelClueById(Long id) { return RelClueMapper.selectRelClueById(id); }
    public List<RelClue> selectRelClueList(RelClue entity) { return RelClueMapper.selectRelClueList(entity); }
    public int insertRelClue(RelClue entity) { return RelClueMapper.insertRelClue(entity); }
    public int updateRelClue(RelClue entity) { return RelClueMapper.updateRelClue(entity); }
    public int deleteRelClueById(Long id) { return RelClueMapper.deleteRelClueById(id); }
    public int deleteRelClueByIds(Long[] ids) { return RelClueMapper.deleteRelClueByIds(ids); }
}
