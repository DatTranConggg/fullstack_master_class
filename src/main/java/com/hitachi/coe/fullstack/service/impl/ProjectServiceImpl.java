package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.CommonConstant;
import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.BusinessDomain;
import com.hitachi.coe.fullstack.entity.Project;
import com.hitachi.coe.fullstack.entity.ProjectStatus;
import com.hitachi.coe.fullstack.entity.ProjectTech;
import com.hitachi.coe.fullstack.entity.ProjectType;
import com.hitachi.coe.fullstack.entity.SkillSet;
import com.hitachi.coe.fullstack.exceptions.CoEException;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.model.ExcelErrorDetail;
import com.hitachi.coe.fullstack.model.ExcelResponseModel;
import com.hitachi.coe.fullstack.model.IProjectModel;
import com.hitachi.coe.fullstack.model.ImportResponse;
import com.hitachi.coe.fullstack.model.ProjectImportModel;
import com.hitachi.coe.fullstack.model.ProjectModel;
import com.hitachi.coe.fullstack.model.ProjectSearchModel;
import com.hitachi.coe.fullstack.model.ProjectUpdateModel;
import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.model.common.ErrorLineModel;
import com.hitachi.coe.fullstack.model.common.ErrorModel;
import com.hitachi.coe.fullstack.repository.BusinessDomainRepository;
import com.hitachi.coe.fullstack.repository.BusinessUnitRepository;
import com.hitachi.coe.fullstack.repository.PracticeRepository;
import com.hitachi.coe.fullstack.repository.ProjectRepository;
import com.hitachi.coe.fullstack.repository.ProjectTypeRepository;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.service.EmployeeLevelService;
import com.hitachi.coe.fullstack.service.ProjectService;
import com.hitachi.coe.fullstack.service.ProjectTechService;
import com.hitachi.coe.fullstack.transformation.BusinessDomainTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectModelTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectSearchModelTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectTransformer;
import com.hitachi.coe.fullstack.transformation.ProjectTypeTransformer;
import com.hitachi.coe.fullstack.transformation.SkillSetTransformer;
import com.hitachi.coe.fullstack.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private BusinessDomainRepository businessDomainRepository;
    @Autowired
    private ProjectTransformer projectTransformer;
    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    @Autowired
    private ProjectModelTransformer projectModelTransformer;
    @Autowired
    private ProjectSearchModelTransformer projectSearchModelTransformer;
    @Autowired
    private ProjectTechService projectTechService;
    @Autowired
    private EmployeeLevelService employeeLevelService;

    @Autowired
    private SkillSetRepository skillSetRepository;

    @Autowired
    private SkillSetTransformer skillSetTransformer;

    @Autowired
    private BusinessDomainTransformer businessDomainTransformer;

    @Autowired
    private ProjectTypeTransformer projectTypeTransformer;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Override
    public ImportResponse importProjectExcel(ExcelResponseModel listOfProject) {
        ImportResponse importResponse = new ImportResponse();
        int totalSuccess = 0;
        HashMap<Integer, Object> dataList = listOfProject.getData();
        List<ExcelErrorDetail> errorList = listOfProject.getErrorDetails();
        List<ErrorModel> errorModelList = ErrorModel.importErrorDetails(errorList);
        List<BusinessDomain> businessDomainList = businessDomainRepository.findAll();
        List<SkillSet> skillSetList = skillSetRepository.findAll();
        List<String> skillSetListName = skillSetList.stream().map(SkillSet::getName).collect(Collectors.toList());
        List<String> businessDomainsName = businessDomainList.stream().map(BusinessDomain::getName).collect(Collectors.toList());

        for (Map.Entry<Integer, Object> entry : dataList.entrySet()) {
            ProjectImportModel projectImport = (ProjectImportModel) entry.getValue();
            Project project = projectRepository.findByCode(projectImport.getProjectCode());

            String[] tech = projectImport.getSkillSetList().split(",");

            boolean flag = true;
            for (String word : tech) {
                if(!skillSetListName.contains(word)){
                    flag = false;
                }
            }

            ProjectStatus projectStatus = null;
            switch (projectImport.getProjectStatus().trim()) {
                case "Closed":
                    projectStatus = ProjectStatus.CLOSE;
                    break;
                case "On-going":
                    projectStatus = ProjectStatus.OPEN;
                    break;
                case "On-hold":
                    projectStatus = ProjectStatus.ON_HOLD;
                    break;
                case "Investigate":
                    projectStatus = ProjectStatus.POC;
                    break;
                case "Re-open":
                    projectStatus = ProjectStatus.REOPEN;
                    break;
                default:
                    break;
            }


            if (ObjectUtils.isEmpty(project) && businessDomainsName.contains(projectImport.getBusinessDomainList()) && flag && projectStatus!=null)
            {
                Project projectInsert = new Project();
                projectInsert.setCode(projectImport.getProjectCode());
                projectInsert.setCustomerName(projectImport.getCustomerName());
                projectInsert.setName(projectImport.getProjectName());
                projectInsert.setStatus(projectStatus);
                projectInsert.setStartDate(projectImport.getStartDate());
                projectInsert.setEndDate(projectImport.getEndDate());
                projectInsert.setDescription(projectImport.getDescription());
                projectInsert.setProjectManager(projectImport.getProjectManager());
                projectInsert.setCreatedBy("admin");
                projectInsert.setCreated(new Date());
                projectInsert.setStatus(projectStatus);
                ProjectType projectType = new ProjectType();
                projectType.setId(1);
                projectInsert.setProjectType(projectType);
                projectInsert.setBusinessDomain(businessDomainList.get(businessDomainsName.indexOf(projectImport.getBusinessDomainList())));

                totalSuccess++;

                Project project1 =  projectRepository.save(projectInsert);
                projectTechService.addProjectListSkill(project1,tech);

            } else {
                errorModelList.add(new ErrorModel(entry.getKey(), ErrorLineModel.importProjectErrorDetails(project,projectStatus == null ? projectImport.getProjectStatus() : null,
                        businessDomainsName,skillSetListName, projectImport.getBusinessDomainList(), projectImport.getSkillSetList())));
            }
        }

        ErrorModel.sortModelsByLine(errorModelList);
        importResponse.setTotalRows(listOfProject.getTotalRows());
        importResponse.setErrorRows(errorModelList.size());
        importResponse.setSuccessRows(totalSuccess);
        importResponse.setErrorList(errorModelList);
        return importResponse;
    }

    @Override
    public Integer add(ProjectModel projectModel) {
        Project existingProjectList = projectRepository.findByCode(projectModel.getCode());
        if (existingProjectList==null) {
            ProjectStatus projectStatus = null;
            for (ProjectStatus status : ProjectStatus.values()) {
                if (projectModel.getStatus() == status.getValue()){
                    projectStatus = status;
                }
            }
            if (projectStatus == null){
                    throw new InvalidDataException(ErrorConstant.PROJECT_STATUS_NOT_EXIST,
                            ErrorConstant.MESSAGE_STATUS_PROJECT_NOT_EXIST);
            }
            projectTypeRepository.findById(projectModel.getProjectType().getId())
                    .orElseThrow(() -> new InvalidDataException(ErrorConstant.PROJECT_TYPE_NOT_NULL,
                            ErrorConstant.MESSAGE_PROJECT_TYPE_NOT_NULL));

            businessDomainRepository.findById(projectModel.getBusinessDomain().getId())
                    .orElseThrow(() -> new InvalidDataException(ErrorConstant.BUSINESS_DOMAIN_NOT_NULL,
                            ErrorConstant.MESSAGE_BUSINESS_DOMAIN_NOT_NULL));

            Project project = projectModelTransformer.apply(projectModel);
            if (null == project) {
                throw new InvalidDataException(ErrorConstant.CODE_PROJECT_NULL, ErrorConstant.MESSAGE_PROJECT_NULL);
            }
            project.setCreated(new Date());

            Project optProject = projectRepository.save(project);
            projectModel.getSkillSets().forEach(skill ->{
                projectTechService.addProjectSkill(optProject, skill);
            });


            return optProject.getId();
        } else {
            throw new InvalidDataException(ErrorConstant.CODE_PROJECT_EXIST,
                    ErrorConstant.MESSAGE_CODE_PROJECT_EXIST);
        }
    }

	@Override
	public Page<ProjectSearchModel> searchProjects(String keyword, String bdName,String ptName, String projectManager, Integer status,
			String fromDateStr, String toDateStr, Integer no, Integer limit, String sortBy, Boolean desc){

		// sort by field of project
		Sort sort = Sort.by(sortBy);
		if (desc != null) {
			sort = sort.descending();
		}
		Timestamp fromDate = CommonUtils.convertStringToTimestamp(fromDateStr);
		Timestamp toDate = CommonUtils.convertStringToTimestamp(toDateStr);

        ProjectStatus statusSearch = null;
        if (status != null) {
            for (ProjectStatus statusList : ProjectStatus.values()) {
                if (status == statusList.getValue()){
                    statusSearch = ProjectStatus.valueOf(status);
                    break;
                }
            }
            if(statusSearch == null) {
                statusSearch = ProjectStatus.ERROR;
            }
        }
		// return page of list projects
		return projectRepository.filterProjects(keyword, bdName, ptName, projectManager, statusSearch , fromDate, toDate,
				PageRequest.of(no, limit, sort)).map(p ->projectSearchModelTransformer.apply(p));
		// convert from projectEntity to projectModel
	}

	@Override
    public ProjectModel update(final ProjectUpdateModel projectModel) {

        final Optional<Project> project = projectRepository.findById(projectModel.getProjectId());
        final Optional<BusinessDomain> businessDomain = businessDomainRepository.findById(projectModel.getBusinessDomainId());
        final Optional<ProjectType> projectType = projectTypeRepository.findById(projectModel.getProjectTypeId());
        final Project isExistedProject = projectRepository.findByCode(projectModel.getCode());
        final Timestamp startDate = CommonUtils.convertStringToTimestamp(projectModel.getStartDate());
        final Timestamp endDate = CommonUtils.convertStringToTimestamp(projectModel.getEndDate());
        final ProjectStatus projectStatus = ProjectStatus.valueOf(projectModel.getStatus());

        if (project.isEmpty()) {
            throw new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND, ErrorConstant.MESSAGE_PROJECT_NOT_FOUND);
        }

        Project existingProject = project.get();

        if (Objects.requireNonNull(startDate).after(endDate)){
            throw new CoEException(ErrorConstant.CODE_INVALID_START_DATE_END_DATE, ErrorConstant.MESSAGE_INVALID_START_DATE_END_DATE);
        }

        if (projectType.isEmpty()){
            throw new CoEException(ErrorConstant.CODE_PROJECT_TYPE_DO_NOT_EXIST, ErrorConstant.MESSAGE_PROJECT_TYPE_DO_NOT_EXIST);
        }

        if (businessDomain.isEmpty()){
            throw new CoEException(ErrorConstant.CODE_BUSINESS_DOMAIN_DO_NOT_EXIST, ErrorConstant.MESSAGE_BUSINESS_DOMAIN_DO_NOT_EXIST);
        }

        if (ObjectUtils.isEmpty(isExistedProject)){
            existingProject.setCode(projectModel.getCode());
        }

        projectTechService.deleteProjectTechByProject(existingProject.getId());
        existingProject.setName(projectModel.getName());
        existingProject.setDescription(projectModel.getDescription());
        existingProject.setCustomerName(projectModel.getCustomerName());
        existingProject.setProjectManager(projectModel.getProjectManager());
        existingProject.setStatus(projectStatus);
        existingProject.setStartDate(CommonUtils.convertStringToTimestamp(projectModel.getStartDate()));
        existingProject.setEndDate(CommonUtils.convertStringToTimestamp(projectModel.getEndDate()));
        existingProject.setUpdated(new Date(System.currentTimeMillis()));
        existingProject.setUpdatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
        existingProject.setBusinessDomain(businessDomain.get());
        existingProject.setProjectType(projectType.get());
        projectRepository.save(existingProject);
        projectModel.getProjectsTech().forEach(projectTechId -> {
            final Optional<SkillSet> skillSet = skillSetRepository.findById(projectTechId);
            if (skillSet.isEmpty()){
                throw new CoEException(ErrorConstant.CODE_SKILL_SET_DO_NOT_EXIST, ErrorConstant.MESSAGE_SKILL_SET_DO_NOT_EXIST);
            }
            final ProjectTech projectTech = new ProjectTech();
            projectTech.setProject(existingProject);
            projectTech.setSkillSet(skillSet.get());
            projectTech.setCreated(new Date(System.currentTimeMillis()));
            projectTech.setCreatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
            projectTechService.saveProjectTech(projectTech);
        });

        return projectTransformer.apply(existingProject);

    }


	@Override
	public ProjectModel closeProject(Integer projectId) {
        Project existingProject = projectRepository.findById(projectId)
	            .orElseThrow(() -> new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND,
	                    ErrorConstant.MESSAGE_PROJECT_NOT_FOUND));

        if(existingProject.getStatus().equals(ProjectStatus.CLOSE)){
            throw new CoEException(ErrorConstant.CODE_PROJECT_ALREADY_CLOSE, ErrorConstant.MESSAGE_PROJECT_ALREADY_CLOSE);
        }

        existingProject.setStatus(ProjectStatus.CLOSE);
        projectRepository.save(existingProject);
        return projectTransformer.apply(existingProject);
	}

    @Override
    public ProjectModel getProjectDetailById(Integer id){
        final List<IProjectModel> iProjectModels = projectRepository.getProjectDetailById(id);

        if(ObjectUtils.isEmpty(iProjectModels)) throw new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND, ErrorConstant.MESSAGE_PROJECT_NOT_FOUND);

        final ProjectModel projectModel = new ProjectModel();
        final List<SkillSetModel> skillSetModels = new ArrayList<>();
        final Optional<BusinessDomain> businessDomain;
        final Optional<ProjectType> projectType = projectTypeRepository.findById(iProjectModels.get(0).getProjectTypeId());

        if (!ObjectUtils.isEmpty(iProjectModels.get(0).getBusinessDomainId())){
            businessDomain = businessDomainRepository.findById(iProjectModels.get(0).getBusinessDomainId());
            if (businessDomain.isEmpty()){
                throw new CoEException(ErrorConstant.CODE_BUSINESS_DOMAIN_DO_NOT_EXIST, ErrorConstant.MESSAGE_BUSINESS_DOMAIN_DO_NOT_EXIST);
            }
            projectModel.setBusinessDomain(businessDomainTransformer.apply(businessDomain.get()));
        } else {
            projectModel.setBusinessDomain(new BusinessDomainModel());
        }


        if (projectType.isEmpty()){
            throw new CoEException(ErrorConstant.CODE_PROJECT_TYPE_DO_NOT_EXIST, ErrorConstant.MESSAGE_PROJECT_TYPE_DO_NOT_EXIST);
        }

        if(!ObjectUtils.isEmpty(iProjectModels.get(0).getSkillSetId())){
            for (IProjectModel iProjectModel : iProjectModels) {
                final Optional<SkillSet> skillSet = skillSetRepository.findById(iProjectModel.getSkillSetId());
                if (skillSet.isEmpty()){
                    throw new CoEException(ErrorConstant.CODE_SKILL_SET_DO_NOT_EXIST, ErrorConstant.MESSAGE_SKILL_SET_DO_NOT_EXIST);
                }
                skillSetModels.add(skillSetTransformer.apply(skillSet.get()));
            }
        }

        projectModel.setProjectType(projectTypeTransformer.apply(projectType.get()));
        projectModel.setId(iProjectModels.get(0).getProjectId());
        projectModel.setCode(iProjectModels.get(0).getCode());
        projectModel.setName(iProjectModels.get(0).getProjectName());
        projectModel.setProjectManager(iProjectModels.get(0).getProjectManager());
        projectModel.setDescription(iProjectModels.get(0).getDescription());
        projectModel.setStartDate(iProjectModels.get(0).getStartDate());
        projectModel.setEndDate(iProjectModels.get(0).getEndDate());
        projectModel.setStatus(iProjectModels.get(0).getStatus());
        projectModel.setCustomerName(iProjectModels.get(0).getCustomerName());
        projectModel.setSkillSets(skillSetModels);

        return projectModel;
    }

    @Override
    public ProjectModel updateProjectStatus(final Integer projectId, final Integer status){
        final Optional<Project> project = projectRepository.findById(projectId);
        final ProjectStatus projectStatus = ProjectStatus.valueOf(status);

        if (project.isEmpty()) {
            throw new CoEException(ErrorConstant.CODE_PROJECT_NOT_FOUND, ErrorConstant.MESSAGE_PROJECT_NOT_FOUND);
        }

        Project existingProject = project.get();
        existingProject.setUpdated(new Date(System.currentTimeMillis()));
        existingProject.setUpdatedBy(CommonConstant.CREATED_BY_ADMINISTRATOR);
        existingProject.setStatus(projectStatus);
        projectRepository.save(existingProject);
        return projectTransformer.apply(existingProject);

    }
}
