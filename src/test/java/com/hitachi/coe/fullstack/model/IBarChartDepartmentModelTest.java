//package com.hitachi.coe.fullstack.model;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//
//import java.math.BigInteger;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.verify;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@TestPropertySource("classpath:application-data-test.properties")
//public class IBarChartDepartmentModelTest {
//
//    @Test
//    public void testGetLevels() {
//        IBarChartDepartmentModel mockModel = mock(IBarChartDepartmentModel.class);
//        when(mockModel.getLevel()).thenReturn("Intern");
//        String level = mockModel.getLevel();
//        verify(mockModel).getLevel();
//        assertEquals("Intern", level);
//    }
//
//    @Test
//    public void testGetQuantity() {
//        IBarChartDepartmentModel mockModel = mock(IBarChartDepartmentModel.class);
//        when(mockModel.getQuantity()).thenReturn(BigInteger.valueOf(3));
//        BigInteger quantity = mockModel.getQuantity();
//        verify(mockModel).getQuantity();
//        assertEquals(BigInteger.valueOf(3), quantity);
//    }
//}