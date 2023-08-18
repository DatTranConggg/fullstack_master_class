package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.LocationModel;

import java.util.List;

public interface LocationService {

    Integer add(LocationModel e);

    Integer updateLocation(LocationModel locationModel);

    /**
     *
     * @return get all location model from location entity
     */
    List<LocationModel> getAllLocations();
}
