package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Practice;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.PracticeModel;
import com.hitachi.coe.fullstack.repository.BusinessUnitRepository;
import com.hitachi.coe.fullstack.repository.PracticeRepository;
import com.hitachi.coe.fullstack.service.PracticeService;
import com.hitachi.coe.fullstack.transformation.PracticeModelTransformer;
import com.hitachi.coe.fullstack.transformation.PracticeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PracticeServiceImpl implements PracticeService {

	@Autowired
	private PracticeRepository practiceRepository;

	@Autowired
	private BusinessUnitRepository businessUnitRepository;

	@Autowired
	private PracticeModelTransformer practiceModelTransformer;

	@Autowired
	private PracticeTransformer practiceTransformer;

	@Override
	public PracticeModel updatePractice(PracticeModel practiceModel) {

//        Practice practiceDb = practiceRepository.findById(practiceModel.getId()).orElse(null);
//        BusinessUnit businessUnit = businessUnitRepository.findById(practiceModel.getBusinessUnit().getId()).orElse(null);
//
//        if (practiceDb == null) {
//            throw new InvalidDataException(ErrorConstant.CODE_PRACTICE_DO_NOT_EXIST, ErrorConstant.MESSAGE_PRACTICE_DO_NOT_EXIST);
//        }
//        if (businessUnit == null) {
//            throw new InvalidDataException(ErrorConstant.CODE_BUSINESS_UNIT_DO_NOT_EXIST, ErrorConstant.MESSAGE_BUSINESS_UNIT_DO_NOT_EXIST);
//        }
//        if (practiceDb != null && businessUnit != null) {
//            Practice existingPractice = practiceDb;
//            existingPractice.setBusinessUnit(practiceModel.getBusinessUnit());
//            existingPractice.setCode(practiceModel.getCode());
//            existingPractice.setName(practiceModel.getName());
//            existingPractice.setDescription(practiceModel.getDescription());
//            existingPractice.setManager(practiceModel.getManager());
//            existingPractice.setUpdatedBy(practiceModel.getUpdatedBy());
//            existingPractice.setUpdated(practiceModel.getUpdated());
//
//            practiceRepository.save(existingPractice);
//            return practiceTransformer.apply(existingPractice);
//        } else {
//            return null;
//        }
		return null;

	}

	@Override
	public Integer createPractice(PracticeModel practice) {

		Practice practiceCode = practiceRepository.findByCode(practice.getCode());
		if (practiceCode != null) {
			throw new InvalidDataException(ErrorConstant.CODE_PRACTICE_DUPLICATE,
					ErrorConstant.MESSAGE_PRACTICE_DUPLICATE);
		}
//		BusinessUnit businessUnit = businessUnitRepository.findById(practice.getBusinessUnit().getId()).orElse(null);
//		if (businessUnit == null) {
//			throw new InvalidDataException(ErrorConstant.CODE_BUSINESS_UNIT_NULL,
//					ErrorConstant.MESSAGE_BUSINESS_UNIT_NULL);
//		}
		Practice prac = practiceModelTransformer.apply(practice);
		if (prac == null) {
			throw new InvalidDataException(ErrorConstant.CODE_PRACTICE_NULL, ErrorConstant.MESSAGE_PRACTICE_NULL);
		}
//		prac.setBusinessUnit(businessUnit);
		prac.setCreated(new Date());
		prac.setUpdated(new Date());

		return practiceRepository.save(prac).getId();
	}

	/**
	 * @return list of Practices
	 * @author PhanNguyen
	 */
	@Override
	public List<PracticeModel> getAllPractice() {
		return practiceTransformer.applyList(practiceRepository.findAll());
	}

	/**
	 * @return Practice by id
	 * @author PhanNguyen
	 */
	@Override
	public PracticeModel getPracticeById(Integer id) {
		Optional<Practice> practiceModel = practiceRepository.findById(id);
		if(!practiceModel.isPresent()) {
			return null;
		}
		return practiceTransformer.apply(practiceModel.get());
	}

	/**
	 * @author lam
	 * @param buId business unit id
	 * @return list of practices that this business unit have
	 */
	@Override
	public List<PracticeModel> getPracticesByBusinessUnitId(Integer buId) {
		return practiceRepository.findAllByBusinessUnitId(buId).stream().map(practiceTransformer).collect(Collectors.toList());
	}

}
