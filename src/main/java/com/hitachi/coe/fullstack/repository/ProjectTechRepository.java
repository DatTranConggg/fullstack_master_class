package com.hitachi.coe.fullstack.repository;

import com.hitachi.coe.fullstack.entity.ProjectTech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTechRepository extends JpaRepository<ProjectTech, Integer> {

    /**
     * Delete Project Tech based on the provided conditions.
     *
     * @param projectId stand for project id
     * @author tquangpham
     *
     */
    @Modifying
    @Query("DELETE FROM ProjectTech pt WHERE pt.project.id = :projectId")
    void deleteByProject(@Param("projectId") Integer projectId);

    /**
     * Find Project Tech based on the provided conditions.
     *
     * @param projectId stand for project id
     * @author tquangpham
     * @return list of project tech that match the project id
     */
    @Query("SELECT pt FROM ProjectTech pt WHERE pt.project.id = :projectId")
    List<ProjectTech> findByProjectId(@Param("projectId") Integer projectId);
}
