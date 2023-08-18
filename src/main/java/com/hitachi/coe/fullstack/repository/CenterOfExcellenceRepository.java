package com.hitachi.coe.fullstack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;

@Repository
public interface CenterOfExcellenceRepository extends JpaRepository<CenterOfExcellence, Integer> {
	/**
	 * @return list of center of excellence from the database
	 * @author PhanNguyen
	 */

	@Query(value = "SELECT coe FROM CenterOfExcellence coe")
	List<CenterOfExcellence> getCenterOfExcellences();

	/**
	 * @param id from center of excellence on database
	 * @return list of center of excellence id by id param
	 * @author PhanNguyen
	 */
	@Query(value = "SELECT coe FROM CenterOfExcellence coe WHERE coe.id = :id")
	CenterOfExcellence getCenterOfExcellencesById(@Param("id") Integer id);
}
