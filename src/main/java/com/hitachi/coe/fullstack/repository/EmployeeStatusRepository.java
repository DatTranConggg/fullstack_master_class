package com.hitachi.coe.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.hitachi.coe.fullstack.entity.EmployeeStatus;


@Repository
public interface  EmployeeStatusRepository  extends JpaRepository<EmployeeStatus, Integer>  {
	@Query(value = "INSERT INTO public.employee_status( status_date, employee_id, status) VALUES (now(), ?1, 0) RETURNING *;", nativeQuery = true)
	EmployeeStatus createDeleteStatusEmployee(@PathVariable("id") Integer id);
}
