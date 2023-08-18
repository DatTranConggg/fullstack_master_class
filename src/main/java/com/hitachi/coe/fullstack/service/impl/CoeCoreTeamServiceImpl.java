package com.hitachi.coe.fullstack.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.constant.StatusConstant;
import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.entity.Employee;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.repository.CenterOfExcellenceRepository;
import com.hitachi.coe.fullstack.repository.CoeCoreTeamRepository;
import com.hitachi.coe.fullstack.repository.EmployeeRepository;
import com.hitachi.coe.fullstack.service.CoeCoreTeamService;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamModelTransformer;
import com.hitachi.coe.fullstack.transformation.CoeCoreTeamTransformer;
import com.hitachi.coe.fullstack.validator.CoeCoreTeamValidate;

@Service
@Transactional
public class CoeCoreTeamServiceImpl implements CoeCoreTeamService {
	@Autowired
	private CoeCoreTeamTransformer coeCoreTeamTransformer;

	@Autowired
	private CoeCoreTeamModelTransformer coeCoreTeamModelTransformer;

	@Autowired
	private CoeCoreTeamRepository coeCoreTeamRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CenterOfExcellenceRepository centerOfExcellenceRepository;

	@Autowired
	private CoeCoreTeamValidate coeCoreTeamValidate;

	@Override
	public Optional<CoeCoreTeamModel> findCoeCoreTeamById(Integer id) {
		Optional<CoeCoreTeam> coeCoreTeam = coeCoreTeamRepository.findById(id);
		return coeCoreTeam.map(value -> Optional.ofNullable(coeCoreTeamTransformer.apply(value))).orElse(null);
	}

	@Override
	public Integer createCoeCoreTeam(CoeCoreTeamModel coeCoreTeamMd) {
//		TODO 
//		CenterOfExcellence centerOfExcellence = centerOfExcellenceRepository
//				.findById(coeCoreTeamMd.getCenterOfExcellence().getId()).orElse(null);
//		Change to Center of Excellence model
//		CoeCoreTeam coeCoreTeamEt = coeCoreTeamModelTransformer.apply(coeCoreTeamMd);
//		if (coeCoreTeamEt == null) {
//			throw new InvalidDataException(ErrorConstant.CODE_COE_CORE_TEAM, ErrorConstant.MESSAGE_COE_CORE_TEAM);
//		}
//		CenterOfExcellence centerOfExcellence = centerOfExcellenceRepository
//				.findById(coeCoreTeamMd.getCenterOfExcellence().getId()).orElse(null);
//		if (centerOfExcellence == null) {
//			throw new InvalidDataException(ErrorConstant.CODE_CENTER_OF_EXCELLENCE_TEAM,
//					ErrorConstant.MESSAGE_CENTER_OF_EXCELLENCE_TEAM);
//		}
//		CoeCoreTeam coeCoreTeamTemp = coeCoreTeamRepository.findByCode(coeCoreTeamMd.getCode());
//		if (coeCoreTeamTemp != null) {
//			throw new InvalidDataException(ErrorConstant.CODE_CODE_DUPLICATE, ErrorConstant.MESSAGE_CODE_DUPLICATE);
//		}
//		coeCoreTeamEt.setCenterOfExcellence(centerOfExcellence);
//		coeCoreTeamEt.setCreated(new Date());
//		coeCoreTeamEt.setUpdated(new Date());
//		return coeCoreTeamTransformer.apply(coeCoreTeamRepository.save(coeCoreTeamEt)).getId();
		return null;
	}

	@Override
	public Integer addMembersToCoeCoreTeam(Integer coeCoreTeamId, List<Integer> employeeIds) {
		if (employeeIds != null && employeeIds.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_LIST_ID_NOT_EMPTY,
					ErrorConstant.MESSAGE_EMPLOYEE_LIST_ID_NOT_EMPTY);
		}

		List<Employee> EmployeeListId = employeeRepository.findAllById(employeeIds);
		if (EmployeeListId.isEmpty()) {
			throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND,
					ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}

		CoeCoreTeam existingCoeCoreTeam = coeCoreTeamRepository.findById(coeCoreTeamId).orElse(null);
		if (existingCoeCoreTeam == null) {
			throw new InvalidDataException(ErrorConstant.MESSAGE_COE_TEAM_NOT_FOUND,
					ErrorConstant.MESSAGE_COE_TEAM_NOT_FOUND);
		}

		for (Employee employeeId : EmployeeListId) {
			employeeId.setCoeCoreTeam(existingCoeCoreTeam);
		}

		return employeeRepository.saveAll(EmployeeListId).size();
	}

	@Override
	public Integer updateCoeCoreTeam(CoeCoreTeamModel coeCoreTeamModel) {

//		TO DO 
//		existingCoeCoreTeam.setCenterOfExcellence(coeCoreTeamModel.getCenterOfExcellence());
//		use transformer to transform from model to entity		

//		Optional<CoeCoreTeam> coeCoreTeamDb = coeCoreTeamRepository.findById(coeCoreTeamModel.getId());
//		if (coeCoreTeamDb.isPresent()) {
//			coeCoreTeamValidate.checkCenterOfExcellenceExist(coeCoreTeamModel);
//
//			CoeCoreTeam existingCoeCoreTeam = coeCoreTeamDb.get();
//			Date currentDate = new Date();
//
//			existingCoeCoreTeam.setCode(coeCoreTeamModel.getCode());
//			existingCoeCoreTeam.setName(coeCoreTeamModel.getName());
//			
//			// 4. Validate Center of excellent
//			Optional<CenterOfExcellence> optExistedCOE = Optional.empty();
//			if (!ObjectUtils.isEmpty(coeCoreTeamModel.getCenterOfExcellenceModel())) {
//				optExistedCOE = centerOfExcellenceRepository.findById(coeCoreTeamModel.getCenterOfExcellenceModel().getId());
//			}
//			if (optExistedCOE.isEmpty()) {
//				throw new InvalidDataException(ErrorConstant.CODE_COECORETEAM_PROBLEM,
//						ErrorConstant.MESSAGE_COECORETEAM_PROBLEM);
//			}
//			existingCoeCoreTeam.setCenterOfExcellence(optExistedCOE.get());
//			
//			existingCoeCoreTeam.setCenterOfExcellence(coeCoreTeamModel.getCenterOfExcellence());
//			existingCoeCoreTeam.setSubLeaderId(coeCoreTeamModel.getSubLeaderId());
//			existingCoeCoreTeam.setStatus(coeCoreTeamModel.getStatus());
//			existingCoeCoreTeam.setUpdatedBy(coeCoreTeamModel.getUpdatedBy());
//			existingCoeCoreTeam.setUpdated(currentDate);
//
//			return coeCoreTeamRepository.save(existingCoeCoreTeam).getId();
//		} else {
//			throw new InvalidDataException(ErrorConstant.CODE_COE_TEAM_NOT_FOUND,
//					ErrorConstant.MESSAGE_COE_TEAM_NOT_FOUND);
//		}
		return null;
	}

	@Override
	public Integer removeMembersFromCoeCoreTeam(List<Integer> employeeIds) {
		List<Employee> employees = employeeRepository.findAllById(employeeIds);
		if (employees.size() == 0) {
			throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NOT_FOUND,
					ErrorConstant.MESSAGE_EMPLOYEE_NOT_FOUND);
		}
		Integer defaultCoeCoreTeamId = 0;

		CoeCoreTeam existingCoeCoreTeam = coeCoreTeamRepository.findById(defaultCoeCoreTeamId).orElse(null);

		if (existingCoeCoreTeam == null) {
			throw new InvalidDataException(ErrorConstant.CODE_COE_CORE_TEAM, ErrorConstant.MESSAGE_COE_CORE_TEAM);
		}
		for (Employee emp : employees) {
			emp.setCoeCoreTeam(existingCoeCoreTeam);
		}

		return employeeRepository.saveAll(employees).size();
	}

	@Override
	public boolean deleteCoeCoreTeam(Integer deleteId) {
		Optional<CoeCoreTeam> coeCoreTeamDb = coeCoreTeamRepository.findById(deleteId);
		if (coeCoreTeamDb.isPresent()) {
			CoeCoreTeam existingCoeCoreTeam = coeCoreTeamDb.get();
			if (existingCoeCoreTeam.getStatus() == StatusConstant.STATUS_ACTIVE) {
				existingCoeCoreTeam.setStatus(StatusConstant.STATUS_DELETED);
				coeCoreTeamRepository.save(existingCoeCoreTeam);
				return true;
			} else {
				throw new InvalidDataException(ErrorConstant.CODE_COE_CORE_TEAM_STATUS_DUPLICATE,
						ErrorConstant.MESSAGE_COE_CORE_TEAM_STATUS_DUPLICATE);
			}
		} else {
			throw new InvalidDataException(ErrorConstant.CODE_COE_CORE_TEAM_NOT_FOUND,
					ErrorConstant.MESSAGE_COE_CORE_TEAM_NOT_FOUND);
		}
	}

	/**
	 * @deception create coe to find the coe from the database by id, then compare
	 *            coe with coeCoreTeam to return list of coeCoreTeam
	 * @param coeId is center of excellence id from center of excellence on database
	 * @return list of CoE core team by center of excellence id
	 * @author PhanNguyen
	 */

	@Override
	public List<CoeCoreTeamModel> getAllCoeTeamByCoeId(Integer coeId) {
		CenterOfExcellence coe = centerOfExcellenceRepository.getCenterOfExcellencesById(coeId);
		if (coe == null) {
			return new ArrayList<>();
		}
		return coeCoreTeamTransformer.applyList(coeCoreTeamRepository.getCoeTeamByCoeId(coe));
	}

	/**
	 * @return list of CoE
	 * @author PhanNguyen
	 */
	@Override
	public List<CoeCoreTeamModel> getAllCoeTeam() {
		if (coeCoreTeamRepository.findAll().isEmpty()) {
			return new ArrayList<>();
		}
		return coeCoreTeamTransformer.applyList(coeCoreTeamRepository.findAll());
	}
}
