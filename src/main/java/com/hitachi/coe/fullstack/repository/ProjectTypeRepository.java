package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Integer> {

}
