package com.hitachi.coe.fullstack.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-data-test.properties")
class DataSetBarChartTest {

    @Test
    void testDataSetBarChart() {
        // Create sample data for the DataSetBarChart
        List<Long> data = Arrays.asList(5L, 10L, 15L);
        String label = "Dataset 1";

        // Create a new DataSetBarChart instance
        DataSetBarChart dataSetBarChart = new DataSetBarChart();
        dataSetBarChart.setData(data);
        dataSetBarChart.setLabel(label);

        // Perform assertions to validate the DataSetBarChart data
        assertEquals(data, dataSetBarChart.getData());
        assertEquals(label, dataSetBarChart.getLabel());
    }
}
