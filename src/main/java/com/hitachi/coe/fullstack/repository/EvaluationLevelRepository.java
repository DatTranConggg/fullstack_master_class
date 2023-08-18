package com.hitachi.coe.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hitachi.coe.fullstack.entity.EvaluationLevel;
import com.hitachi.coe.fullstack.entity.Right;

@Repository
public interface EvaluationLevelRepository extends JpaRepository<EvaluationLevel, Integer> {

}
