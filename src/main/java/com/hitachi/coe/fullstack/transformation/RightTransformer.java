package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.Right;
import com.hitachi.coe.fullstack.model.GroupRightModel;
import com.hitachi.coe.fullstack.model.RightModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RightTransformer extends AbstractCopyPropertiesTransformer<Right, RightModel>
        implements EntityToModelTransformer<Right, RightModel, Integer> {

    @Autowired
    GroupRightTransformer groupRightTransformer;

    /**
     * Transformer array entities to array DTO.
     * @author thinhqp
     * @param entities {@link List} of {@link Right}
     * @return {@link List} of {@link RightModel}
     */
    public List<RightModel> applyList(List<Right> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public RightModel apply(Right entity) {
        RightModel model = new RightModel();
        model.setId(entity.getId());
        model.setCode(entity.getCode());
        model.setModule(entity.getModule());
        if (!ObjectUtils.isEmpty(entity.getGroupRights())) {
            List<GroupRightModel> groupRightModels;
            groupRightModels = entity.getGroupRights().stream()
                    .map(groupRight -> groupRightTransformer.apply(groupRight)).collect(Collectors.toList());
            model.setGroupRights(groupRightModels);
        }
        return model;
    }
}
