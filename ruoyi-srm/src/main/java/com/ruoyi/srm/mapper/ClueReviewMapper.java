package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.ClueReview;

public interface ClueReviewMapper
{
    ClueReview selectClueReviewById(Long id);
    List<ClueReview> selectClueReviewList(ClueReview entity);
    int insertClueReview(ClueReview entity);
    int updateClueReview(ClueReview entity);
    int deleteClueReviewById(Long id);
    int deleteClueReviewByIds(Long[] ids);
}
