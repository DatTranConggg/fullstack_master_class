package com.hitachi.coe.fullstack.service.impl;

import java.util.List;

import com.hitachi.coe.fullstack.model.ProjectTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitachi.coe.fullstack.model.BusinessDomainModel;
import com.hitachi.coe.fullstack.repository.BusinessDomainRepository;
import com.hitachi.coe.fullstack.service.BusinessDomainService;
import com.hitachi.coe.fullstack.transformation.BusinessDomainTransformer;

@Service
@Transactional
public class BusinessDomainServiceImpl implements BusinessDomainService {

    @Autowired
    BusinessDomainTransformer businessDomainTransformer;
    @Autowired
    private BusinessDomainRepository businessDomainRepository;

    @Override
    public List<BusinessDomainModel> getBusinessDomains() {

        return businessDomainTransformer.applyList(businessDomainRepository.getBusinessDomains());
    }

    /**
     * Retrieve a list of business domains by practice ID.
     *
     * @author DatCongNguyen
     * @param practiceId The ID of the practice.
     * @return A list of BusinessDomainModel objects.
     */
    @Override
    public List<BusinessDomainModel> getByPractice(Integer practiceId) {
        return businessDomainTransformer.applyList(businessDomainRepository.findByPractice(practiceId));
    }

    @Override
    public List<BusinessDomainModel> getAllBusinessDomain() {
        return businessDomainTransformer.applyList(businessDomainRepository.findAll());
    }
}
