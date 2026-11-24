package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.Project;

public interface IProjectService
{
    Project selectProjectById(Long id);
    List<Project> selectProjectList(Project entity);
    int insertProject(Project entity);
    int updateProject(Project entity);
    int deleteProjectById(Long id);
    int deleteProjectByIds(Long[] ids);
}
