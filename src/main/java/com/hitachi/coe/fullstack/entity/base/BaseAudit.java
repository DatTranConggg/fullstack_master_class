package com.hitachi.coe.fullstack.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * <p>
 * Declares @CreateBy, @CreatedDate, @LastModifiedBy, @LastModifiedDate for entity.
 * </p>
 *
 *
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseAudit {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", updatable = false, nullable = false)
    @CreatedDate
    private Date created;

    @Column(name = "created_by", updatable = false, nullable = false)
    @CreatedBy
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = true)
    @LastModifiedDate
    private Date updated;

    @Column(name = "updated_by", nullable = true)
    @LastModifiedBy
    private String updatedBy;
}
