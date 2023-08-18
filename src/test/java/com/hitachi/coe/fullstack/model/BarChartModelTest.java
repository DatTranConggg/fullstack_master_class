package com.hitachi.coe.fullstack.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
public class BarChartModelTest {

	@Test
	public void testBarChartModelGettersAndSetters() {
		// Create sample data
		List<String> labels = Arrays.asList("Label 1", "Label 2", "Label 3");
		List<Long> data = Arrays.asList(0L, 0L, 0L);
		String datasetLabel = "Dataset Label";

		// Create instances of BarChartModel and DataSetBarChart
		BarChartModel barChartModel = new BarChartModel();
		DataSetBarChart dataSetBarChart = new DataSetBarChart();

		// Set values using setter methods
		barChartModel.setLabels(labels);
		dataSetBarChart.setData(data);
		dataSetBarChart.setLabel(datasetLabel);
		List<DataSetBarChart> list = new ArrayList<>();
		list.add(dataSetBarChart);
		barChartModel.setDatasets(list);
		// Verify getter methods
		assertThat(barChartModel.getLabels()).isEqualTo(labels);
		assertThat(dataSetBarChart.getData()).isEqualTo(data);
		assertThat(dataSetBarChart.getLabel()).isEqualTo(datasetLabel);
		assertThat(barChartModel.getDatasets()).isEqualTo(list);
	}

	@Test
	public void testDataSetBarChartGettersAndSetters() {
		// Create sample data
		List<Long> data = Arrays.asList(1L, 2L, 3L);
		String label = "Dataset Label";

		// Create instance of DataSetBarChart
		DataSetBarChart dataSetBarChart = new DataSetBarChart();

		// Set values using setter methods
		dataSetBarChart.setData(data);
		dataSetBarChart.setLabel(label);

		// Verify getter methods
		assertThat(dataSetBarChart.getData()).isEqualTo(data);
		assertThat(dataSetBarChart.getLabel()).isEqualTo(label);
	}

}
