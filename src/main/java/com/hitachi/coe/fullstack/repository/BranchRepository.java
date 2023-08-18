package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The Interface BranchRepository is used to access Branch table.
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch,Integer> {
    @Query("SELECT b FROM Branch b WHERE b.location.id = :locationId")
    List<Branch> getBranchesByLocation(@Param("locationId") int locationId);
    
	/**
	 * @return list of branch from the database 
	 * @author PhanNguyen
	 */
    @Query ("SELECT b FROM Branch b")
    List<Branch> getAllBranches();
}
