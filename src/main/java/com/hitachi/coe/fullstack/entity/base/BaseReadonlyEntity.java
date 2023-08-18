package com.hitachi.coe.fullstack.entity.base;

import java.io.Serializable;

/**
 * This interface should be implemented by the entities that are not managed by the application
 * i.e. read-only entities or "reference tables"
 */
public interface BaseReadonlyEntity<ID extends Serializable> { //NOSONAR

    ID getId();
}
