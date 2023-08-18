package com.hitachi.coe.fullstack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelErrorDetail {

    Integer rowIndex;

    HashMap<String,String> details;
}
