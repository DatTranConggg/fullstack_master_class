package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface BusinessUnitRepository is used to access BusinessUnit table.
 */
@Repository
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit,Integer> {

}
