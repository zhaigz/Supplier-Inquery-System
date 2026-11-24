package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.SearchHistory;
import com.ruoyi.srm.mapper.SearchHistoryMapper;
import com.ruoyi.srm.service.ISearchHistoryService;

@Service
public class SearchHistoryServiceImpl implements ISearchHistoryService
{
    @Autowired
    private SearchHistoryMapper SearchHistoryMapper;

    public SearchHistory selectSearchHistoryById(Long id) { return SearchHistoryMapper.selectSearchHistoryById(id); }
    public List<SearchHistory> selectSearchHistoryList(SearchHistory entity) { return SearchHistoryMapper.selectSearchHistoryList(entity); }
    public int insertSearchHistory(SearchHistory entity) { return SearchHistoryMapper.insertSearchHistory(entity); }
    public int updateSearchHistory(SearchHistory entity) { return SearchHistoryMapper.updateSearchHistory(entity); }
    public int deleteSearchHistoryById(Long id) { return SearchHistoryMapper.deleteSearchHistoryById(id); }
    public int deleteSearchHistoryByIds(Long[] ids) { return SearchHistoryMapper.deleteSearchHistoryByIds(ids); }
}
