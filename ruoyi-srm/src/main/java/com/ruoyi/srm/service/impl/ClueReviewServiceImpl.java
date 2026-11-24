package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ClueReview;
import com.ruoyi.srm.mapper.ClueReviewMapper;
import com.ruoyi.srm.service.IClueReviewService;

@Service
public class ClueReviewServiceImpl implements IClueReviewService
{
    @Autowired
    private ClueReviewMapper ClueReviewMapper;

    public ClueReview selectClueReviewById(Long id) { return ClueReviewMapper.selectClueReviewById(id); }
    public List<ClueReview> selectClueReviewList(ClueReview entity) { return ClueReviewMapper.selectClueReviewList(entity); }
    public int insertClueReview(ClueReview entity) { return ClueReviewMapper.insertClueReview(entity); }
    public int updateClueReview(ClueReview entity) { return ClueReviewMapper.updateClueReview(entity); }
    public int deleteClueReviewById(Long id) { return ClueReviewMapper.deleteClueReviewById(id); }
    public int deleteClueReviewByIds(Long[] ids) { return ClueReviewMapper.deleteClueReviewByIds(ids); }
}
