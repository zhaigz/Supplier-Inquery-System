package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.SearchHistory;

public interface SearchHistoryMapper
{
    SearchHistory selectSearchHistoryById(Long id);
    List<SearchHistory> selectSearchHistoryList(SearchHistory entity);
    int insertSearchHistory(SearchHistory entity);
    int updateSearchHistory(SearchHistory entity);
    int deleteSearchHistoryById(Long id);
    int deleteSearchHistoryByIds(Long[] ids);
}
