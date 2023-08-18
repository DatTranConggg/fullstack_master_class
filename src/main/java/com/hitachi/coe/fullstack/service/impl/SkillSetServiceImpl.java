package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.repository.SkillSetRepository;
import com.hitachi.coe.fullstack.service.SkillSetService;
import com.hitachi.coe.fullstack.transformation.SkillSetTransformer;
import com.hitachi.coe.fullstack.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillSetServiceImpl implements SkillSetService {

	@Autowired
	SkillSetRepository skillSetRepository;

	@Autowired
	SkillSetTransformer skillSetTransformer;

	/**
	 * @param name stand for skill name
	 * @return list of skills base on query name that user has typed in
	 * @author PhanNguyen
	 */
	@Override
	public List<SkillSetModel> searchSkillSetByName(String name) {
		String findByName = StringUtil.combineString(name, "%");
		return skillSetTransformer.applyList(skillSetRepository.searchSkillSetByName(findByName));
	}

}
