package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.ProjectFeedback;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.ProjectFeedbackModel;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.repository.EvaluationLevelRepository;
import com.hitachi.coe.fullstack.repository.ProjectFeedbackRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.service.ProjectFeedbackService;
import com.hitachi.coe.fullstack.transformation.ProjectFeedbackModelTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectFeedbackTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ProjectFeedbackServiceImpl implements ProjectFeedbackService {

    @Autowired
    ProjectFeedbackRepository projectFeedbackRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EvaluationLevelRepository evaluationLevelRepository;

    @Autowired
    ProjectFeedbackModelTransformer projectFeedbackModelTransformer;

    @Autowired
    ProjectFeedbackTransformer projectFeedbackTransformer;

    @Override
    public Integer add(ProjectFeedbackModel projectFeedbackModel) {

            projectRepository.findById(projectFeedbackModel.getProject().getId())
                    .orElseThrow(() -> new InvalidDataException(ErrorConstant.CODE_PROJECT_NOT_NULL,
                            ErrorConstant.MESSAGE_PROJECT_NOT_NULL));

            employeeRepository.findById(projectFeedbackModel.getEmployee().getId())
                    .orElseThrow(() -> new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NULL,
                            ErrorConstant.MESSAGE_EMPLOYEE_NULL));

            evaluationLevelRepository.findById(projectFeedbackModel.getEvaluationLevel().getId())
                    .orElseThrow(() -> new InvalidDataException(ErrorConstant.CODE_EVALUATION_LEVEL_NOT_NULL,
                            ErrorConstant.MESSAGE_EVALUATION_LEVEL_NOT_NULL));
            ProjectFeedback em = projectFeedbackModelTransformer.apply(projectFeedbackModel);
            if (null == em) {
                throw new InvalidDataException(ErrorConstant.CODE_PROJECT_FEEDBACK_NULL, ErrorConstant.MESSAGE_PROJECT_FEEDBACK_NULL);
            }
            em.setCreated(new Date());
            return projectFeedbackRepository.save(em).getId();
    }
}
