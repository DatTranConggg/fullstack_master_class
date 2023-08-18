package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.CoeUtilization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoeUtilizationRepository extends JpaRepository<CoeUtilization,Integer> {

}
