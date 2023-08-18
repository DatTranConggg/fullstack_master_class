package com.hitachi.coe.fullstack.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import com.hitachi.coe.fullstack.repository.CenterOfExcellenceRepository;
import com.hitachi.coe.fullstack.service.CenterOfExcellenceService;
import com.hitachi.coe.fullstack.transformation.CenterOfExcellenceTransformer;

@Service
public class CenterOfExcellenceServiceImpl implements CenterOfExcellenceService{
	
	@Autowired
	private CenterOfExcellenceTransformer centerOfExcellenceTransformer;
	
	@Autowired
	private CenterOfExcellenceRepository centerOfExcellenceRepository;
	
	/**
	 * @return list of center of excellence on database
	 * @author PhanNguyen
	 */
	
	@Override
	public List<CenterOfExcellenceModel> getAllCenterOfExcellence() {
		return centerOfExcellenceTransformer.applyList(centerOfExcellenceRepository.getCenterOfExcellences());
	}

}
