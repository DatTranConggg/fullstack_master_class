package com.hitachi.coe.fullstack.transformation;

import com.hitachi.coe.fullstack.entity.GroupRight;
import com.hitachi.coe.fullstack.model.GroupRightModel;
import com.hitachi.coe.fullstack.transformation.base.AbstractCopyPropertiesTransformer;
import com.hitachi.coe.fullstack.transformation.base.EntityToModelTransformer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupRightTransformer extends AbstractCopyPropertiesTransformer<GroupRight, GroupRightModel>
        implements EntityToModelTransformer<GroupRight, GroupRightModel, Integer> {

    /**
     * Transformer array entities to array DTO.
     *
     * @param entities {@link List} of {@link GroupRight}
     * @return {@link List} of {@link GroupRightModel}
     * @author thinhqp
     */
    public List<GroupRightModel> applyList(List<GroupRight> entities) {
        if (null == entities || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::apply)
                .collect(Collectors.toList());
    }
    public GroupRightModel apply(GroupRight entity) {
        GroupRightModel model = new GroupRightModel();
        model.setId(entity.getId());
        model.setGroupId(entity.getGroupId());
        model.setRightId(entity.getRight().getId());
        return model;
    }
}
