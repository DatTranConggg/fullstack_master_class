package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Level;
import com.hitachi.coe.fullstack.model.LevelModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This LevelTransformer is transform Entity to DTO.
 *
 * @author hchantran
 */
@Component
public class LevelTransformer extends AbstractCopyPropertiesTransformer<Level, LevelModel>
        implements EntityToModelTransformer<Level, LevelModel, Integer> {
    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link Level}
     * @return {@link List} of {@link LevelModel}
     */
    public List<LevelModel> applyList(List<Level> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
    @Override
    public LevelModel apply(Level entity) {
        LevelModel model = new LevelModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setCode(entity.getCode());
        model.setDescription(entity.getDescription());
        return model;
    }
}
