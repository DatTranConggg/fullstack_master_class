package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.LevelModel;

import java.util.List;

/**
 * This service class is to GET data for Levels table.
 *
 * @author hchantran
 */
public interface LevelService {

    /**
     * Get all Levels available in Levels table.
     *
     * @author hchantran
     * @return A list of all levels in the Levels table.
     */
    List<LevelModel> getAllLevels();
}
