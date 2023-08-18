package com.hitachi.coe.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hitachi.coe.fullstack.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
