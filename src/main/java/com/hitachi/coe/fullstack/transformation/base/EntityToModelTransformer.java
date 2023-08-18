package com.hitachi.coe.fullstack.transformation.base;

import java.io.Serializable;

import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;
import com.hitachi.coe.fullstack.model.base.BaseReadonlyModel;

/**
 * A transformer interface that converts an instance of {@link BaseReadonlyEntity} to an instance of {@link BaseReadonlyModel}
 *
 *  M generics for MODEL
 *  E generics for ENTITY
 *  I generics for ID
 */
public interface EntityToModelTransformer<E extends BaseReadonlyEntity<ID>, M extends BaseReadonlyModel<ID>, ID extends Serializable> //NOSONAR
        extends
            Transformer<E, M> {

}
