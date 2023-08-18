package com.hitachi.coe.fullstack.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataSetBarChart {
	private List<Long> data;
	private String label;
}
