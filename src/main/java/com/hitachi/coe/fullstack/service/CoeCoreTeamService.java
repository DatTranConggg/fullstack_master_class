package com.hitachi.coe.fullstack.service;

import java.util.List;
import java.util.Optional;

import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;

public interface CoeCoreTeamService {

    Optional<CoeCoreTeamModel> findCoeCoreTeamById(Integer id);

    Integer createCoeCoreTeam(CoeCoreTeamModel coeCoreTeam);

    boolean deleteCoeCoreTeam(Integer deleteId);

    Integer addMembersToCoeCoreTeam(Integer coeCoreTeamId, List<Integer> employeeIds);

    Integer updateCoeCoreTeam(CoeCoreTeamModel coeCoreTeamModel);

    Integer removeMembersFromCoeCoreTeam(List<Integer> employeeIds);
    
	/**
	 * @deception create coe to find the coe from the database by id, then compare
	 *             coe with coeCoreTeam to return list of coeCoreTeam
	 * @param coeId is center of excellence id from center of excellence on database
	 * @return list of CoE core team by center of excellence id
	 * @author PhanNguyen
	 */

    List<CoeCoreTeamModel> getAllCoeTeamByCoeId(Integer coeId);
    
	/**
	 * @return list of CoE
	 * @author PhanNguyen
	 */

    List<CoeCoreTeamModel> getAllCoeTeam();
}
