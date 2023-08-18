package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing Location entities.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {

}
