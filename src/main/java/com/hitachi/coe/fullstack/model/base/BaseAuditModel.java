package com.hitachi.coe.fullstack.model.base;

import java.io.Serializable;
import java.util.Date;

public interface BaseAuditModel<ID extends Serializable> extends BaseReadonlyModel<ID> { //NOSONAR
    String getCreatedBy();
    Date getCreated();
    
    void setCreatedBy(String user);
    void setCreated(Date createdDate);
    
    String getUpdatedBy();
    Date getUpdated();
    
    void setUpdatedBy(String user);
    void setUpdated(Date updatedDate);
    
    void setId(ID id);
}
