package com.hitachi.coe.fullstack.validator;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.entity.CoeCoreTeam;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.repository.CenterOfExcellenceRepository;
import com.hitachi.coe.fullstack.repository.CoeCoreTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CoeCoreTeamValidate {

    @Autowired
    private CenterOfExcellenceRepository centerOfExcellenceRepository;

    @Autowired
    private CoeCoreTeamRepository coeCoreTeamRepository;

    public void checkCenterOfExcellenceExist(CoeCoreTeamModel coeCoreTeamModel) {
        Optional<CenterOfExcellence> centerOfExcellence = centerOfExcellenceRepository.findById(coeCoreTeamModel.getCenterOfExcellenceId());
        if (!centerOfExcellence.isPresent()) {
            throw new InvalidDataException(ErrorConstant.CODE_CENTER_OF_EXCELLENCE_NOT_FOUND, ErrorConstant.MESSAGE_CENTER_OF_EXCELLENCE_NOT_FOUND);
        }
    }

    public void chekCoeTeamExist(CoeCoreTeamModel coeCoreTeamModel) {
        Optional<CoeCoreTeam> coeCoreTeam = coeCoreTeamRepository.findById(coeCoreTeamModel.getId());
        if (!coeCoreTeam.isPresent()) {
            throw new InvalidDataException(ErrorConstant.CODE_COE_TEAM_NOT_FOUND, ErrorConstant.MESSAGE_COE_TEAM_NOT_FOUND);
        }
    }
}
