package com.hitachi.coe.fullstack.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.BusinessUnit;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.BusinessUnitModel;
import com.hitachi.coe.fullstack.repository.BusinessUnitRepository;
import com.hitachi.coe.fullstack.service.BusinessUnitService;
import com.hitachi.coe.fullstack.transformation.BusinessUnitModelTransformer;
import com.hitachi.coe.fullstack.transformation.BusinessUnitTransformer;
import com.hitachi.coe.fullstack.transformation.PracticeTransformer;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessUnitServiceImpl implements BusinessUnitService {


    private final BusinessUnitTransformer businessUnitTransformer;

    private final BusinessUnitRepository businessUnitRepository;

    private final BusinessUnitModelTransformer businessUnitModelTransformer;

    private final PracticeTransformer practiceTransformer;

    @Override
    public Optional<BusinessUnitModel> findBusinessUnitById(Integer id) {
        Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(id);
        return businessUnit.map(value -> Optional.ofNullable(businessUnitTransformer.apply(value))).orElse(null);
    }

    @Override
    public Integer add(BusinessUnitModel businessUnitModel) {

        BusinessUnit businessUnit = businessUnitModelTransformer.apply(businessUnitModel);
        if (null == businessUnit) {
            throw new InvalidDataException(ErrorConstant.CODE_EMPLOYEE_NULL, ErrorConstant.MESSAGE_EMPLOYEE_NULL);
        }

        return businessUnitRepository.save(businessUnit).getId();
    }

    @Override
    public Integer updateBusinessUnit(BusinessUnitModel businessUnitModel) {
        Optional<BusinessUnit> businessUnitDb = businessUnitRepository.findById(businessUnitModel.getId());
        if (businessUnitDb.isPresent()) {
            BusinessUnit existingBusinessUnit = businessUnitDb.get();

            existingBusinessUnit.setCode(businessUnitModel.getCode());
            existingBusinessUnit.setName(businessUnitModel.getName());
            existingBusinessUnit.setDescription(businessUnitModel.getDescription());
            existingBusinessUnit.setManager(businessUnitModel.getManager());
            existingBusinessUnit.setUpdatedBy(businessUnitModel.getUpdatedBy());
            existingBusinessUnit.setUpdated(businessUnitModel.getUpdated());


            return businessUnitRepository.save(existingBusinessUnit).getId();
        } else {
            throw new InvalidDataException(ErrorConstant.CODE_BUSINESS_UNIT_NOT_FOUND, ErrorConstant.MESSAGE_BUSINESS_UNIT_NOT_FOUND);
        }
    }

    /**
     * @author lam
     * @return All business unit in the database
     */
    @Override
    public List<BusinessUnitModel> findAll() {
        return businessUnitRepository.findAll().stream().map(businessUnitTransformer::apply).collect(Collectors.toList());
    }
}
