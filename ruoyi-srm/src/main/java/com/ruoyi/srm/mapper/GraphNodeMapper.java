package com.ruoyi.srm.mapper;

import java.util.List;
import com.ruoyi.srm.domain.GraphNode;

public interface GraphNodeMapper
{
    GraphNode selectGraphNodeById(Long id);
    List<GraphNode> selectGraphNodeList(GraphNode entity);
    int insertGraphNode(GraphNode entity);
    int updateGraphNode(GraphNode entity);
    int deleteGraphNodeById(Long id);
    int deleteGraphNodeByIds(Long[] ids);
}
