package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.RelClue;

public interface RelClueMapper
{
    RelClue selectRelClueById(Long id);
    List<RelClue> selectRelClueList(RelClue entity);
    int insertRelClue(RelClue entity);
    int updateRelClue(RelClue entity);
    int deleteRelClueById(Long id);
    int deleteRelClueByIds(Long[] ids);
}
