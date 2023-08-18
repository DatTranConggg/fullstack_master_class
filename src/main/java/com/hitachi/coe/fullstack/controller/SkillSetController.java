package com.hitachi.coe.fullstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.SkillSetModel;
import com.hitachi.coe.fullstack.service.SkillSetService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/skill-set")
public class SkillSetController {
	@Autowired
	private SkillSetService skillSetService;

	/**
	 * @param name stand for skill name
	 * @return list of skills base on query name that user has typed in
	 * @author PhanNguyen
	 */
	@GetMapping("/list-skills-by-name")
	@ApiOperation("This API will return a list of branches")
	public ResponseEntity<List<SkillSetModel>> searchSkillSetByName(@RequestParam(required = true) String name) {
		List<SkillSetModel> skills = skillSetService.searchSkillSetByName(name);
		return ResponseEntity.ok(skills);
	}
}
