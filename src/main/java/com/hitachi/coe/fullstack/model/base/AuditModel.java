package com.hitachi.coe.fullstack.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.coe.fullstack.util.DateTimeSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = { "id" })
@ToString
public class AuditModel<ID extends Serializable> implements BaseAuditModel<ID> { //NOSONAR
    
    /**
     * <p>Description of this field.</p>
     */
    private static final long serialVersionUID = 1L;
    
    ID id;
    
    @JsonSerialize(using = DateTimeSerializer.class)
    Date created;
    
    String createdBy;
    
    @JsonSerialize(using = DateTimeSerializer.class)
    Date updated;
    
    String updatedBy;   
}
