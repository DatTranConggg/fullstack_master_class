//package com.hitachi.coe.fullstack.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.TestPropertySource;
//
//import com.hitachi.coe.fullstack.entity.BusinessUnit;
//import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
//import com.hitachi.coe.fullstack.model.BusinessUnitModel;
//import com.hitachi.coe.fullstack.repository.BusinessUnitRepository;
//import com.hitachi.coe.fullstack.transformation.BusinessUnitModelTransformer;
//import com.hitachi.coe.fullstack.transformation.BusinessUnitTransformer;
//
//@SpringBootTest
//@TestPropertySource("classpath:application-data-test.properties")
//class BusinessUnitServiceTest {
//
//    @InjectMocks
//    private BusinessUnitService businessUnitService;
//
//    @MockBean
//    private BusinessUnitTransformer businessUnitTransformer;
//
//    @MockBean
//    private BusinessUnitRepository businessUnitRepository;
//
//    @MockBean
//    private BusinessUnitModelTransformer businessUnitModelTransformer;
//
//    @Test
//    void testFindBusinessUnitById() {
//        BusinessUnitModel businessUnitModel = new BusinessUnitModel();
//        businessUnitModel.setId(1);
//        BusinessUnit businessUnit = new BusinessUnit();
//        businessUnit.setId(1);
//
//        Mockito.when(businessUnitRepository.findById(1)).thenReturn(Optional.of(businessUnit));
//
//        Mockito.when(businessUnitTransformer.apply(businessUnit)).thenReturn(businessUnitModel);
//
//        Optional<BusinessUnitModel> result = businessUnitService.findBusinessUnitById(1);
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(businessUnitModel, result.get());
//    }
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void createBusinessUnit_Success() {
//        BusinessUnitModel bu = new BusinessUnitModel();
//        bu.setId(1);
//        bu.setCode("1");
//        bu.setName("1");
//        bu.setCreatedBy("NguyenHai");
//
//        BusinessUnit returnData = new BusinessUnit();
//        returnData.setId(1);
//        returnData.setCode("1");
//        returnData.setName("1");
//        returnData.setCreatedBy("NguyenHai");
//
//        when(businessUnitModelTransformer.apply(bu)).thenReturn(returnData);
//
//        when(businessUnitRepository.save(returnData)).thenReturn(returnData);
//
//        Integer actual = businessUnitService.add(bu);
//        assertEquals(1, actual);
//    }
//
//    @Test
//    void createBusinessUnit_UnSuccess() {
//        BusinessUnitModel bu = new BusinessUnitModel();
//
//        Throwable throwable = assertThrows(Exception.class, () -> businessUnitService.add(bu));
//
//        assertEquals(InvalidDataException.class, throwable.getClass());
//        assertEquals("employee.null", throwable.getMessage());
//    }
//
//    @Test
//    void updateBusinessUnit() {
//
//        int businessUnitId = 1;
//        BusinessUnitModel businessUnitModel = new BusinessUnitModel();
//        businessUnitModel.setId(businessUnitId);
//        businessUnitModel.setCode("123");
//        businessUnitModel.setName("DS");
//        businessUnitModel.setDescription("BU DS");
//        businessUnitModel.setManager("Hong Duc Nguyen");
//
//        BusinessUnit exitBusinessUnit = new BusinessUnit();
//        exitBusinessUnit.setId(businessUnitId);
//        exitBusinessUnit.setCode("123");
//        exitBusinessUnit.setName("DS");
//        exitBusinessUnit.setDescription("BU DS");
//        exitBusinessUnit.setManager("Hong Duc Nguyen");
//
//
//        when(businessUnitRepository.findById(businessUnitId)).thenReturn(Optional.of(exitBusinessUnit));
//        when(businessUnitRepository.save(any(BusinessUnit.class))).thenReturn(exitBusinessUnit);
//
//        when(businessUnitTransformer.apply(exitBusinessUnit)).thenReturn(businessUnitModel);
//
//        Integer actual = businessUnitService.updateBusinessUnit(businessUnitModel);
//
//        assertEquals(exitBusinessUnit.getId(), actual);
//
//    }
//
//    @Test
//    void updateBusinessUnit_Exception() {
//
//        int businessUnitId = 1;
//        BusinessUnitModel businessUnitModel = new BusinessUnitModel();
//        businessUnitModel.setId(businessUnitId);
//
//        Throwable throwable = assertThrows(Exception.class, () -> businessUnitService.updateBusinessUnit(businessUnitModel));
//
//        assertEquals(InvalidDataException.class, throwable.getClass());
//        assertEquals("business.unit.not.found", throwable.getMessage());
//
//    }
//
//}
