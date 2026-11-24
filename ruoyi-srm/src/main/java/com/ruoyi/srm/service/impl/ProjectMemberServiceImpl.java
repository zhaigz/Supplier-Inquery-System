package com.ruoyi.srm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.srm.domain.ProjectMember;
import com.ruoyi.srm.mapper.ProjectMemberMapper;
import com.ruoyi.srm.service.IProjectMemberService;

@Service
public class ProjectMemberServiceImpl implements IProjectMemberService
{
    @Autowired
    private ProjectMemberMapper ProjectMemberMapper;

    public ProjectMember selectProjectMemberById(Long id) { return ProjectMemberMapper.selectProjectMemberById(id); }
    public List<ProjectMember> selectProjectMemberList(ProjectMember entity) { return ProjectMemberMapper.selectProjectMemberList(entity); }
    public int insertProjectMember(ProjectMember entity) { return ProjectMemberMapper.insertProjectMember(entity); }
    public int updateProjectMember(ProjectMember entity) { return ProjectMemberMapper.updateProjectMember(entity); }
    public int deleteProjectMemberById(Long id) { return ProjectMemberMapper.deleteProjectMemberById(id); }
    public int deleteProjectMemberByIds(Long[] ids) { return ProjectMemberMapper.deleteProjectMemberByIds(ids); }
}
