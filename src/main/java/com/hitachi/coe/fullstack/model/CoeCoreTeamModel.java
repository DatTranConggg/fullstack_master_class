package com.hitachi.coe.fullstack.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.hitachi.coe.fullstack.model.base.AuditModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoeCoreTeamModel extends AuditModel<Integer> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4899877294913301040L;

	@Length(max = 50)
    @NotNull
    private String code;

    @Length(max = 100)
    @NotNull
    private String name;

    @NotNull
    private Integer status;

    @NotNull
    private Integer subLeaderId;

    private int centerOfExcellenceId;
    
    private String centerOfExcellenceName;

}
