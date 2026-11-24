package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.GraphNode;

public interface IGraphNodeService
{
    GraphNode selectGraphNodeById(Long id);
    List<GraphNode> selectGraphNodeList(GraphNode entity);
    int insertGraphNode(GraphNode entity);
    int updateGraphNode(GraphNode entity);
    int deleteGraphNodeById(Long id);
    int deleteGraphNodeByIds(Long[] ids);
}
