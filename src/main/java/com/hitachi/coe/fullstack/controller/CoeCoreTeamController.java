package com.hitachi.coe.fullstack.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.constant.Constants;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.service.CoeCoreTeamService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/")
public class CoeCoreTeamController {
	@Autowired
	private CoeCoreTeamService coeCoreTeamService;

	@PostMapping("coe-core-team/create")
	@ApiOperation("Add coe-core-team")
	public ResponseEntity<Object> createCoeCoreTeam(@Validated @RequestBody CoeCoreTeamModel coeCoreTeam) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.ID, String.valueOf(coeCoreTeamService.createCoeCoreTeam(coeCoreTeam)));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("coe-core-team/update")
	@ApiOperation("This api update coe-core-team will return status and coe-core-team id")
	public ResponseEntity<Object> updateCoeCoreTeam(@Validated @RequestBody CoeCoreTeamModel coeCoreTeamModel) {

		Map<String, String> response = new HashMap<>();
		response.put(Constants.ID, String.valueOf(coeCoreTeamService.updateCoeCoreTeam(coeCoreTeamModel)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("coe-core-team/add-members/{coeCoreTeamId}")
	@ApiOperation("Add member to coe-core-team")
	public ResponseEntity<Object> addMembersToCoeCoreTeam(@PathVariable Integer coeCoreTeamId,
			@RequestBody List<Integer> employeeIds) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SIZE,
				String.valueOf(coeCoreTeamService.addMembersToCoeCoreTeam(coeCoreTeamId, employeeIds)));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("coe-core-team/remove-members")
	@ApiOperation("Remove members from group")
	public ResponseEntity<Object> removeMembersFromCoeCoreTeam(@RequestBody List<Integer> employeeIds) {
		Map<String, String> response = new HashMap<>();
		response.put(Constants.SIZE, String.valueOf(coeCoreTeamService.removeMembersFromCoeCoreTeam(employeeIds)));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("coe-core-team/delete/{id}")
	@ApiOperation("This api update coe-core-team will return status and coe-core-team id")
	public ResponseEntity<Object> deleteCoeCoreTeam(@PathVariable Integer id) {

		Map<String, String> response = new HashMap<>();
		response.put(Constants.RESULT, String.valueOf(coeCoreTeamService.deleteCoeCoreTeam(id)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	/**
	 * @param id is center of excellence id from center of excellence on database
	 * @return list of CoE core team by center of excellence id
	 * @author PhanNguyen
	 */
	@GetMapping("coe-core-team/list-coe-team/{coeId}")
	@ApiOperation("This api will return list of coe core team by coe id ")
	public ResponseEntity<List<CoeCoreTeamModel>> getCoeCoreTeamByCoreId(@PathVariable("coeId") Integer id) {
		return new ResponseEntity<>(coeCoreTeamService.getAllCoeTeamByCoeId(id), HttpStatus.OK);
	}
	
	/**
	 * @return list of CoE core team
	 * @author PhanNguyen
	 */
	@GetMapping("coe-core-team/list-coe-team")
	@ApiOperation("This api will return list of coe core team by coe id ")
	public ResponseEntity<List<CoeCoreTeamModel>> getCoeCoreTeam() {
		return new ResponseEntity<>(coeCoreTeamService.getAllCoeTeam(), HttpStatus.OK);
	}
}
