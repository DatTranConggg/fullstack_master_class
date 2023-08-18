package com.hitachi.coe.fullstack.service.impl;


import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.LevelModel;
import com.hitachi.coe.fullstack.repository.LevelRepository;
import com.hitachi.coe.fullstack.service.LevelService;
import com.hitachi.coe.fullstack.transformation.LevelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private LevelTransformer levelTransformer;

    @Override
    public List<LevelModel> getAllLevels() {
        List<Level> employeeLevel = levelRepository.getAllLevels();
        return levelTransformer.applyList(employeeLevel);
    }

}
