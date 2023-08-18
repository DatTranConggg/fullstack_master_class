package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.CenterOfExcellence;
import com.hitachi.coe.fullstack.model.CenterOfExcellenceModel;
import com.hitachi.coe.fullstack.model.CoeCoreTeamModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This CenterOfExcellenceTransformer is transform entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
@RequiredArgsConstructor
public class CenterOfExcellenceTransformer extends AbstractCopyPropertiesTransformer<CenterOfExcellence, CenterOfExcellenceModel>
        implements EntityToModelTransformer<CenterOfExcellence, CenterOfExcellenceModel, Integer> {


	private final CoeCoreTeamTransformer coeCoreTeamTransformer;
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link CenterOfExcellence}
     * @return {@link List} of {@link CenterOfExcellenceModel}
     */
    public List<CenterOfExcellenceModel> applyList(List<CenterOfExcellence> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public CenterOfExcellenceModel apply(CenterOfExcellence orig) {
    	CenterOfExcellenceModel centerOfExcellence = new CenterOfExcellenceModel();
    	List<CoeCoreTeamModel> coeCoreTeams = orig.getCoeCoreTeams().stream().map(coeCoreTeamTransformer::apply).collect(Collectors.toList());
    	centerOfExcellence.setId(orig.getId());
    	centerOfExcellence.setCreated(orig.getCreated());
    	centerOfExcellence.setCreatedBy(orig.getCreatedBy());
    	centerOfExcellence.setUpdated(orig.getUpdated());
    	centerOfExcellence.setUpdatedBy(orig.getUpdatedBy());
    	centerOfExcellence.setCode(orig.getCode());
    	centerOfExcellence.setName(orig.getName());
    	centerOfExcellence.setCoeCoreTeamModel(coeCoreTeams);
    	return centerOfExcellence;
    }
}
