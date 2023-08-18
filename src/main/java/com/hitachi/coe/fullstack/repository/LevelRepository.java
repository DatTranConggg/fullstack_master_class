package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.IPieChartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Level entities.
 */
@Repository
public interface LevelRepository extends JpaRepository<Level,Integer> {

    /**
     * Get all Levels available in Levels table.
     *
     * @author hchantran
     * @return A list of all Levels in the Levels table.
     */
    @Query(value = "SELECT lv.id , lv.code, lv.name, lv.description, lv.created_by , lv.created_date," +
            " lv.updated_by , lv.updated_date from level lv ", nativeQuery = true)
    List<Level> getAllLevels();

    @Query(value = "Select result.name as label, result.total_employees as data " +
            "from piechartlevel(Cast(Cast(:branchId as text)as Integer), " +
            "Cast(Cast(:coeCoreTeamId as text)as Integer), " +
            "Cast(Cast(:coe_id as text)as Integer)) as result ", nativeQuery = true)
    List<IPieChartModel> piechartlevel(@Param("branchId")Integer branchId, @Param("coeCoreTeamId")Integer coeCoreTeamId,
                                       @Param("coe_id")Integer coe_id);


}
