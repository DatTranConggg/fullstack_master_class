package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.BranchModel;

import java.util.List;
import java.util.Optional;

public interface BranchService {

    Optional<BranchModel> findBranchById(Integer id);

    boolean updateBranch(BranchModel branchModel);

    Integer add(BranchModel branchModel);
    
	/**
	 * @return list of branch from the database 
	 * @author PhanNguyen
	 */
    List<BranchModel> getAllBranches();

    /**
     *
     * @param id location id
     * @return a list of branches model this location have
     */
    List<BranchModel> getAllBranchesByLocationId(int id);
}
