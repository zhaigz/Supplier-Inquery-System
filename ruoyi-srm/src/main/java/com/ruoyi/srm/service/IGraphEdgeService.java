package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.GraphEdge;

public interface IGraphEdgeService
{
    GraphEdge selectGraphEdgeById(Long id);
    List<GraphEdge> selectGraphEdgeList(GraphEdge entity);
    int insertGraphEdge(GraphEdge entity);
    int updateGraphEdge(GraphEdge entity);
    int deleteGraphEdgeById(Long id);
    int deleteGraphEdgeByIds(Long[] ids);
}
