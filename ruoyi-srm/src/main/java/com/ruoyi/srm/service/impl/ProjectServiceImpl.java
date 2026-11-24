package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.Project;
import com.ruoyi.srm.mapper.ProjectMapper;
import com.ruoyi.srm.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService
{
    @Autowired
    private ProjectMapper ProjectMapper;

    public Project selectProjectById(Long id) { return ProjectMapper.selectProjectById(id); }
    public List<Project> selectProjectList(Project entity) { return ProjectMapper.selectProjectList(entity); }
    public int insertProject(Project entity) { return ProjectMapper.insertProject(entity); }
    public int updateProject(Project entity) { return ProjectMapper.updateProject(entity); }
    public int deleteProjectById(Long id) { return ProjectMapper.deleteProjectById(id); }
    public int deleteProjectByIds(Long[] ids) { return ProjectMapper.deleteProjectByIds(ids); }
}
