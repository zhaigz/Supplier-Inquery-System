package com.ruoyi.srm.service;

import java.util.List;
import com.ruoyi.srm.domain.ProjectMember;

public interface IProjectMemberService
{
    ProjectMember selectProjectMemberById(Long id);
    List<ProjectMember> selectProjectMemberList(ProjectMember entity);
    int insertProjectMember(ProjectMember entity);
    int updateProjectMember(ProjectMember entity);
    int deleteProjectMemberById(Long id);
    int deleteProjectMemberByIds(Long[] ids);
}
