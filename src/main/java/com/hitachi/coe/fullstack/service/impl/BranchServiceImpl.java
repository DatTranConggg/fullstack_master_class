package com.hitachi.coe.fullstack.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Branch;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.BranchModel;
import com.hitachi.coe.fullstack.repository.BranchRepository;
import com.hitachi.coe.fullstack.repository.LocationRepository;
import com.hitachi.coe.fullstack.service.BranchService;
import com.hitachi.coe.fullstack.transformation.BranchModelTransformer;
import com.hitachi.coe.fullstack.transformation.BranchTransformer;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	BranchTransformer branchTransformer;

	@Autowired
	BranchModelTransformer branchModelTransformer;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Optional<BranchModel> findBranchById(Integer id) {
		Optional<Branch> branch = branchRepository.findById(id);
		return branch.map(value -> Optional.ofNullable(branchTransformer.apply(value))).orElse(null);
	}

	@Override
	public boolean updateBranch(BranchModel branchModel) {
//		TODO
//		Optional<Branch> branchDB = branchRepository.findById(branchModel.getId());
//		if (branchDB.isPresent()) {
//			Branch existingBranch = branchDB.get();
//
//			existingBranch.setCode(branchModel.getCode());
//			existingBranch.setName(branchModel.getName());
//			Optional<Location> getLocation = locationRepository.findById(branchModel.getLocation().getId());
//			if (getLocation.isPresent()) {
//				existingBranch.setLocation(branchModel.getLocation());
//			} else {
//				throw new InvalidDataException(ErrorConstant.CODE_LOCATION_NULL, ErrorConstant.MESSAGE_LOCATION_NULL);
//			}
//			Optional<Branch> branchUpdate = Optional.of(branchRepository.save(existingBranch));
//			if (branchUpdate.isPresent()) {
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
		return false;
	}

	public Integer add(BranchModel branchModel) {
		Branch branch = branchModelTransformer.apply(branchModel);
		if (null == branch) {
			throw new InvalidDataException(ErrorConstant.CODE_BRANCH_NOT_NULL, ErrorConstant.MESSAGE_BRANCH_NOT_NULL);
		}
		branch.setCreated(new Date());
		return branchRepository.save(branch).getId();
	}

	/**
	 * @return list of branch from the database 
	 * @author PhanNguyen
	 */
	
	@Override
	public List<BranchModel> getAllBranches() {
		return branchTransformer.applyList(branchRepository.getAllBranches());
	}

	@Override
	public List<BranchModel> getAllBranchesByLocationId(int id) {
		return branchRepository.getBranchesByLocation(id).stream().map(branchTransformer::apply).collect(Collectors.toList());
	}

}
