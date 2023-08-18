package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;

public interface CenterOfExcellenceService {
	/**
	 * @return list of center of excellence on database
	 * @author PhanNguyen
	 */
	List<CenterOfExcellenceModel> getAllCenterOfExcellence();
}
