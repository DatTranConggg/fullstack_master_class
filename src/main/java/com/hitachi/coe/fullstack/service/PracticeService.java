package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.PracticeModel;

import java.util.List;

/**
 * This class is a service to CRUD data for practice table
 *
 * @author dang.chien
 */
public interface PracticeService {
    PracticeModel updatePractice(PracticeModel practiceModel);

    Integer createPractice(PracticeModel data);
    
    
	/**
	 * @return list Practice
	 * @author PhanNguyen
	 */
    List<PracticeModel> getAllPractice();
    
	/**
	 * @return Practice by id
	 * @author PhanNguyen
	 */
    PracticeModel getPracticeById(Integer id);

	/**
	 * @author lam
	 * @param buId business unit id
	 * @return list of practices that this business unit have
	 */
	List<PracticeModel> getPracticesByBusinessUnitId(Integer buId);
}
