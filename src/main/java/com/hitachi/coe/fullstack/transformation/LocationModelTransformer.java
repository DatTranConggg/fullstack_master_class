package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.model.LocationModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This LocationTransformer is transform entity to DTO.
 *
 * @author lphanhoangle
 */
@Component
public class LocationModelTransformer extends AbstractCopyPropertiesTransformer<LocationModel, Location>
        implements ModelToEntityTransformer<LocationModel, Location, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link Location}
     * @return {@link List} of {@link LocationModel}
     */
    public List<Location> applyList(List<LocationModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
