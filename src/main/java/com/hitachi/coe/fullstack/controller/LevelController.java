package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.model.LevelModel;
import com.hitachi.coe.fullstack.service.LevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class LevelController {

    @Autowired
    private LevelService employeeLevelService;

    /**
     * Get all Levels available in Levels table.
     *
     * @author hchantran
     * @category GET
     * @return A Response Entity of list of all Levels in the levels table.
     */
    @GetMapping("levels")
    @ApiOperation("This api will return list of Levels")
    public ResponseEntity<List<LevelModel>> getAllLevels() {
        return ResponseEntity.ok(
                employeeLevelService.getAllLevels());
    }
}
