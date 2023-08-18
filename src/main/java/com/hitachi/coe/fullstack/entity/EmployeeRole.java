package com.hitachi.coe.fullstack.entity;

import com.hitachi.coe.fullstack.entity.base.BaseAudit;
import com.hitachi.coe.fullstack.entity.base.BaseReadonlyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * The persistent class for the emploeye_role database table.
 */
@Getter
@Setter
@Entity
@Table(name = "employee_role")
public class EmployeeRole extends BaseAudit implements BaseReadonlyEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    // One-to-Many relationship with EmployeeProject
    @OneToMany(mappedBy = "employeeRole", fetch = FetchType.LAZY)
    private List<EmployeeProject> employeeProjects;
}
