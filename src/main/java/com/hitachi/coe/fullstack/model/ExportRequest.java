package com.hitachi.coe.fullstack.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExportRequest {

    String keyWord;

    String typeFile;

    String practiceName;

    String coeCoreTeamName;

    String branchName;

    String fromDateStr;

    String toDateStr;

    Integer pageNo;

    Integer itemPerPage;

    String sortBy;

    @JsonProperty("isAscending")
    boolean isAscending;


}
