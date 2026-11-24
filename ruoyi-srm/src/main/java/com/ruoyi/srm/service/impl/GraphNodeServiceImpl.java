package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.GraphNode;
import com.ruoyi.srm.mapper.GraphNodeMapper;
import com.ruoyi.srm.service.IGraphNodeService;

@Service
public class GraphNodeServiceImpl implements IGraphNodeService
{
    @Autowired
    private GraphNodeMapper GraphNodeMapper;

    public GraphNode selectGraphNodeById(Long id) { return GraphNodeMapper.selectGraphNodeById(id); }
    public List<GraphNode> selectGraphNodeList(GraphNode entity) { return GraphNodeMapper.selectGraphNodeList(entity); }
    public int insertGraphNode(GraphNode entity) { return GraphNodeMapper.insertGraphNode(entity); }
    public int updateGraphNode(GraphNode entity) { return GraphNodeMapper.updateGraphNode(entity); }
    public int deleteGraphNodeById(Long id) { return GraphNodeMapper.deleteGraphNodeById(id); }
    public int deleteGraphNodeByIds(Long[] ids) { return GraphNodeMapper.deleteGraphNodeByIds(ids); }
}
