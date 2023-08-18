package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.BusinessUnitModel;

import java.util.List;
import java.util.Optional;

public interface BusinessUnitService {

    Integer add(BusinessUnitModel businessUnitModel);

    Optional<BusinessUnitModel> findBusinessUnitById(Integer id);

    Integer updateBusinessUnit(BusinessUnitModel businessUnitModel);

    List<BusinessUnitModel> findAll();
}
