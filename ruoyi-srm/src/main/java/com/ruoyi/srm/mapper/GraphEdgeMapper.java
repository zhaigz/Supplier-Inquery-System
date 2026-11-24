package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.GraphEdge;

public interface GraphEdgeMapper
{
    GraphEdge selectGraphEdgeById(Long id);
    List<GraphEdge> selectGraphEdgeList(GraphEdge entity);
    int insertGraphEdge(GraphEdge entity);
    int updateGraphEdge(GraphEdge entity);
    int deleteGraphEdgeById(Long id);
    int deleteGraphEdgeByIds(Long[] ids);
}
