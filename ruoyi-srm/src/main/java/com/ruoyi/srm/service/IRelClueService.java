package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.RelClue;

public interface IRelClueService
{
    RelClue selectRelClueById(Long id);
    List<RelClue> selectRelClueList(RelClue entity);
    int insertRelClue(RelClue entity);
    int updateRelClue(RelClue entity);
    int deleteRelClueById(Long id);
    int deleteRelClueByIds(Long[] ids);
}
