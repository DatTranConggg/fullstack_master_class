package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.model.SkillSetModel;

public interface SkillSetService {

	/**
	 * @param name stand for skill name
	 * @return list of skills base on query name that user has typed in
	 * @author PhanNguyen
	 */
	List<SkillSetModel> searchSkillSetByName(String name);
}
