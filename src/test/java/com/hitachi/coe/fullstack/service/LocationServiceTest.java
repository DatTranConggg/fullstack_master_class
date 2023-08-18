package com.hitachi.coe.fullstack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.LocationModel;
import com.hitachi.coe.fullstack.repository.LocationRepository;
import com.hitachi.coe.fullstack.service.impl.LocationServiceImpl;
import com.hitachi.coe.fullstack.transformation.LocationTransformer;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-data-test.properties")
class LocationServiceTest {
    @MockBean
    LocationRepository locationRepository;

    @Autowired
    LocationServiceImpl locationService;

    @MockBean
    LocationTransformer locationTransformer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateLocation_success() {
        LocationModel model = new LocationModel();
        model.setId(1);
        model.setCode("HN");

        Location locationMock = new Location();
        locationMock.setId(1);
        locationMock.setCode("VN");
        locationMock.setName("Việt Nam");

        when(locationRepository.findById(model.getId())).thenReturn(Optional.of(locationMock));
        when(locationRepository.save(locationMock)).thenReturn(locationMock);

        Integer idResultUpdate = locationService.updateLocation(model);

        assertEquals(idResultUpdate, 1);
    }

    @Test
    void updateLocation_failed() {
        LocationModel model = new LocationModel();
        model.setId(2);
        model.setCode("HN");

        when(locationRepository.findById(model.getId())).thenReturn(Optional.ofNullable(null));

        assertThrows(InvalidDataException.class, () -> locationService.updateLocation(model));
    }

}