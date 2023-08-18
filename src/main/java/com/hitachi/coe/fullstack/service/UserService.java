package com.hitachi.coe.fullstack.service;

import java.util.List;

import com.hitachi.coe.fullstack.model.UserModel;

/**
 * This class is a service to GET data for user table.
 * 
 * @author ktrandangnguyen
 */

public interface UserService {
  /**
   * Get all users available in User table.
   * 
   * @author ktrandangnguyen
   * @return A list of all users in the User table.
   */
  List<UserModel> getAllUsers();
  
  /**
   * Filters users that are in the same group base on group id.
   * 
   * @author ktrandangnguyen
   * @param groupId         The group id to filter by. If null, no filtering
   *                        by group id will be applied.                  
   * 
   * @return A list of users that have the same group id.
   */
  List<UserModel> getUsersByGroupId(Integer groupId);
  
  /**
   * Filters user based on user id.
   * 
   * @author ktrandangnguyen
   * @param id              The user id to filter by. If null, no filtering
   *                        by user id will be applied.                  
   *                        
   * @return An user that matches the provided id.
   */
  UserModel getUserById(Integer id);
}
