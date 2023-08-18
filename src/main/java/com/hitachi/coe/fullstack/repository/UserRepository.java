package com.hitachi.coe.fullstack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hitachi.coe.fullstack.entity.User;

/**
 * The Interface UserRepository is using to access User table.
 *
 * @author ktrandangnguyen
 */
@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{

  /**
   * Get all users available in User table.
   *
   * @author ktrandangnguyen
   * @return A list of all users in the User table.
   */
  @Query(value = "SELECT u.id, u.name, u.email, u.group_id, u.created_by, u.created_date, u.updated_by, u.updated_date "
      + "FROM public.user u", nativeQuery = true)
  List<User> getAllUsers();

  /**
   * Filters users that are in the same group base on group id.
   *
   * @author ktrandangnguyen
   * @param groupId         The group id to filter by. If null, no filtering
   *                        by group id will be applied.
   *
   * @return A list of users that have the same group id.
   */
  @Query(value = "SELECT u.id, u.name, u.email, u.group_id, u.created_by, u.created_date, u.updated_by, u.updated_date "
      + "FROM public.user u "
      + "WHERE u.group_id = :groupId "
      + "ORDER BY u.id ASC", nativeQuery = true)
  List<User> getUsersByGroupId( @Param("groupId") Integer groupId);


  /**
   * Filters user based on user id.
   *
   * @author ktrandangnguyen
   * @param id              The user id to filter by. If null, no filtering
   *                        by user id will be applied.
   *
   * @return An user that matches the provided id.
   */
  @Query(value = "SELECT u.id, u.name, u.email, u.group_id, u.created_by, u.created_date, u.updated_by, u.updated_date "
      + "FROM public.user u "
      + "WHERE u.id = :id ", nativeQuery = true)
  User getUserById( @Param("id") Integer id);

}
