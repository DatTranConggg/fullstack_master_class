package com.hitachi.coe.fullstack.transformation.base;

import java.io.Serializable;

import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;
import com.hitachi.coe.fullstack.model.base.BaseReadonlyModel;

/**
 * A transformer interface that converts an instance of {@link BaseReadonlyModel} to an instance of {@link BaseReadonlyEntity}
 *
 * M generics for MODEL
 * E generics for ENTITY
 * I generics for ID
 */
public interface ModelToEntityTransformer<M extends BaseReadonlyModel<ID>, E extends BaseReadonlyEntity<ID>, ID extends Serializable> //NOSONAR
        extends
            Transformer<M, E> {

}
