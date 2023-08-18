package com.hitachi.coe.fullstack.controller;
import java.util.List;

import com.hitachi.coe.fullstack.model.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.coe.fullstack.model.UserModel;
import com.hitachi.coe.fullstack.service.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * The class UserController is create API to use in front end.
 *
 * @author ktrandangnguyen
 *
 */
@RestController
@RequestMapping("/api/v1/")
public class UserController {
  @Autowired
  private UserService userService;
  
  /**
   * Get all users available in User table.
   * 
   * @author ktrandangnguyen
   * @category GET
   * @return A Response Entity of list of all users in the user table.
   */
  @GetMapping("user")
  @ApiOperation("This api will return list of users")
  public BaseResponse<List<UserModel>> getAllUsers() {
    return new BaseResponse<>(HttpStatus.OK.value(),null,userService.getAllUsers());
  }
  
  /**
   * Filters users that are in the same group base on group id.
   * 
   * @author ktrandangnguyen
   * @category GET
   * @param groupId         The group id to filter by. If null, no filtering
   *                        by group id will be applied.                  
   * 
   * @return A Response Entity of list of users that have the same group id.
   */
  @GetMapping("user/group/{groupId}")
  @ApiOperation("This api will return list of users based on group id")
  public BaseResponse<List<UserModel>> getUsersByGroupId(@PathVariable Integer groupId) {
    return new BaseResponse<>(HttpStatus.OK.value(),null,userService.getUsersByGroupId(groupId));
  }
  
  /**
   * Filters user based on user id.
   *
   * @param id The user id to filter by. If null, no filtering
   *           by user id will be applied.
   * @return A Response Entity of user that matches the provided id.
   * @author ktrandangnguyen
   * @category GET
   */
  @GetMapping("user/{id}")
  @ApiOperation("This api will return list of users based on user id")
  public BaseResponse<UserModel> getUserById(@PathVariable Integer id) {
    return new BaseResponse<>(HttpStatus.OK.value(),null,userService.getUserById(id));
  }
}
