package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillSetRepository extends JpaRepository<SkillSet, Integer> {
	@Query("SELECT skill FROM SkillSet skill")
	List<SkillSet> getAllSkillSet();

	/**
	 * @param name stand for skill name
	 * @return list of skills base on query name that user has typed in
	 * @author PhanNguyen
	 */
	@Query("SELECT skill FROM SkillSet skill WHERE skill.name LIKE :name")
	List<SkillSet> searchSkillSetByName(@Param("name") String name);

}
