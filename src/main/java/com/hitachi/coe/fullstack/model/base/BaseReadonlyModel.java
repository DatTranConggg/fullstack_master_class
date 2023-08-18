package com.hitachi.coe.fullstack.model.base;

import java.io.Serializable;

/**
 * This interface should be implemented by all shared objects (DTOs in other terms).
 */
public interface BaseReadonlyModel <ID extends Serializable> extends Serializable { //NOSONAR
    
    ID getId();
}
