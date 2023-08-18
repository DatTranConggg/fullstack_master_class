package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.LevelModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.ModelToEntityTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LevelModelTransformer extends AbstractCopyPropertiesTransformer<LevelModel, Level>
    implements ModelToEntityTransformer<LevelModel, Level, Integer> {

    /**
     * Transformer array DTO to array entities.
     *
     * @param entities {@link List} of {@link Level}
     * @return {@link List} of {@link LevelModel}
     */
    public List<Level> applyList(List<LevelModel> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
}
