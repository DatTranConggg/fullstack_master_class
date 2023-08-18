package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoeCoreTeamRepository extends JpaRepository<CoeCoreTeam, Integer> {
	CoeCoreTeam findByCode(String code);

	/**
	 * @param coeId from center of excellence entity
	 * @return list of center of excellence id by coeId @param
	 * @author PhanNguyen
	 */
	@Query("SELECT c FROM CoeCoreTeam c WHERE c.centerOfExcellence = :coeId")
	List<CoeCoreTeam> getCoeTeamByCoeId(@Param("coeId") CenterOfExcellence coeId);
}
