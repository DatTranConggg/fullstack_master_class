package com.hitachi.coe.fullstack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Represents an ProjectUpdateModel model
 */
@Getter
@Setter
@NoArgsConstructor
public class ProjectUpdateModel {

    @NotNull(message = "Project id is required")
    @Min(value = 1, message = "Project id must be greater than or equal to 1")
    private Integer projectId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Start date is required")
    private String startDate;

    @NotBlank(message = "End date is required")
    private String endDate;

    @NotBlank(message = "Project manager is required")
    private String projectManager;

    @NotNull(message = "Status is required")
    @Min(value = -1, message = "Status must be greater than or equal to -1")
    private Integer status;

    @NotNull(message = "Business domain id is required")
    @Min(value = 1, message = "Business domain id must be greater than or equal to 1")
    private Integer businessDomainId;

    @NotNull(message = "Project type id is required")
    @Min(value = 1, message = "Project type id must be greater than or equal to 1")
    private Integer projectTypeId;

    @NotEmpty(message = "List of projects technical is required")
    private List<Integer> projectsTech;

}
