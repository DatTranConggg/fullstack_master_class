package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.BusinessDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface BusinessDomainRepository is using to access BusinessDomain
 * table.
 */
@Repository
public interface BusinessDomainRepository extends JpaRepository<BusinessDomain, Integer> {

	@Query(value = "SELECT bd.* FROM business_domain bd", nativeQuery = true)
	List<BusinessDomain> getBusinessDomains();

	/**
	 * Retrieve a list of business domains by practice ID.
	 *
	 * @author DatCongNguyen
	 * @param practiceId The ID of the practice.
	 * @return A list of BusinessDomain objects.
	 */
	@Query("select b from BusinessDomain b where b.practice.id = ?1")
	List<BusinessDomain> findByPractice(Integer practiceId);
}
