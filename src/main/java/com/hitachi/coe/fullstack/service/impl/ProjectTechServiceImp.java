package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.entity.ProjectTech;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.repository.ProjectTechRepository;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.service.ProjectTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectTechServiceImp implements ProjectTechService {
    @Autowired
    SkillSetRepository skillSetRepository;

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectTechRepository projectTechRepository;
    @Override
    public void addProjectSkill(Project project, SkillSetModel skillSetModel) {
        ProjectTech projectTech = new ProjectTech();
        Optional<SkillSet> skillSet = skillSetRepository.findById(skillSetModel.getId());
        if (skillSet.isPresent()) {
            projectTech.setSkillSet(skillSet.get());
        } else {
            throw new InvalidDataException(ErrorConstant.CODE_LIST_ID_OF_SKILL_NULL,
                    "Can not find skill set in database");
        }
        projectTech.setProject(project);
        projectTech.setCreatedBy("admin");
        projectTech.setCreated(project.getCreated());

        projectTechRepository.save(projectTech);
    }

    @Override
    public void addProjectListSkill(Project project, String[] tech) {
        for (String word : tech) {
            ProjectTech projectTech = new ProjectTech();
            List<SkillSet> skillSet = skillSetRepository.searchSkillSetByName(word);
            if (skillSet.size() != 0) {
                projectTech.setSkillSet(skillSet.get(0));
            } else {
                throw new InvalidDataException(ErrorConstant.CODE_LIST_ID_OF_SKILL_NULL,
                        "Can not find skill set in database");
            }
            projectTech.setProject(project);
            projectTech.setCreatedBy("admin");
            projectTech.setCreated(project.getCreated());

            projectTechRepository.save(projectTech);
        }

    }

    @Override
    public void deleteProjectTechByProject(final Integer projectId){
        if(ObjectUtils.isEmpty(projectId)){
            throw new CoEException(ErrorConstant.CODE_PROJECT_NULL, ErrorConstant.MESSAGE_PROJECT_NULL);
        }

        List<ProjectTech> projectTechList = projectTechRepository.findByProjectId(projectId);

        if(ObjectUtils.isEmpty(projectTechList)){
            return;
        }

        projectTechRepository.deleteByProject(projectId);
    }

    @Override
    public void saveProjectTech(final ProjectTech projectTech){
        if (ObjectUtils.isEmpty(projectTech)){
            throw new CoEException(ErrorConstant.CODE_PROJECT_TECH_DO_NOT_EXIST, ErrorConstant.MESSAGE_PROJECT_TECH_DO_NOT_EXIST);
        }
        projectTechRepository.save(projectTech);
    }
}
