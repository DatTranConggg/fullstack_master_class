package com.hitachi.coe.fullstack.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BarChartModel {
	private List<String> labels;
	private List<DataSetBarChart> datasets;
}
