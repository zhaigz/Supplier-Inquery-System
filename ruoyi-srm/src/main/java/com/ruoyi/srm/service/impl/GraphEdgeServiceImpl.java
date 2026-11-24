package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.GraphEdge;
import com.ruoyi.srm.mapper.GraphEdgeMapper;
import com.ruoyi.srm.service.IGraphEdgeService;

@Service
public class GraphEdgeServiceImpl implements IGraphEdgeService
{
    @Autowired
    private GraphEdgeMapper GraphEdgeMapper;

    public GraphEdge selectGraphEdgeById(Long id) { return GraphEdgeMapper.selectGraphEdgeById(id); }
    public List<GraphEdge> selectGraphEdgeList(GraphEdge entity) { return GraphEdgeMapper.selectGraphEdgeList(entity); }
    public int insertGraphEdge(GraphEdge entity) { return GraphEdgeMapper.insertGraphEdge(entity); }
    public int updateGraphEdge(GraphEdge entity) { return GraphEdgeMapper.updateGraphEdge(entity); }
    public int deleteGraphEdgeById(Long id) { return GraphEdgeMapper.deleteGraphEdgeById(id); }
    public int deleteGraphEdgeByIds(Long[] ids) { return GraphEdgeMapper.deleteGraphEdgeByIds(ids); }
}
