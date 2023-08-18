package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.Practice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Interface PracticeRepository is used to access Practice table.
 */
@Repository
public interface PracticeRepository extends JpaRepository<Practice, Integer> {
    public Practice findByCode(String code);

    /**
     *
     * @param buId Business Unit id
     * @return a list of Practice that this Business Unit have
     */
    List<Practice> findAllByBusinessUnitId(Integer buId);

}
