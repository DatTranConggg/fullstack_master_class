package com.hitachi.coe.fullstack.service.impl;

import com.hitachi.coe.fullstack.constant.ErrorConstant;
import com.hitachi.coe.fullstack.entity.Location;
import com.hitachi.coe.fullstack.exceptions.InvalidDataException;
import com.hitachi.coe.fullstack.model.LocationModel;
import com.hitachi.coe.fullstack.repository.LocationRepository;
import com.hitachi.coe.fullstack.service.LocationService;
import com.hitachi.coe.fullstack.transformation.BranchTransformer;
import com.hitachi.coe.fullstack.transformation.LocationModelTransformer;
import com.hitachi.coe.fullstack.transformation.LocationTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private  final LocationRepository locationRepository;

    private  final LocationTransformer locationTransformer;

    private final LocationModelTransformer locationModelTransformer;

    private final BranchTransformer branchTransformer;

    @Override
    public Integer add(LocationModel locationModel) {
        Location location = locationModelTransformer.apply(locationModel);
        if(null == location){
            throw new InvalidDataException(ErrorConstant.CODE_LOCATION_NULL, ErrorConstant.MESSAGE_LOCATION_NULL);
        }
        location.setCreated(new Date());
        return locationRepository.save(location).getId();
    }

    @Override
    public Integer updateLocation(LocationModel locationModel) {
        Optional<Location> locationDb = locationRepository.findById(locationModel.getId());
        if (locationDb.isPresent()) {
            Location existingLocation = locationDb.get();
            existingLocation.setName(locationModel.getName());
            existingLocation.setCode(locationModel.getCode());
            existingLocation.setBranches(new ArrayList<>());

            return locationRepository.save(existingLocation).getId();
        } else {
            throw new InvalidDataException(ErrorConstant.CODE_LOCATION_NOT_FOUND, ErrorConstant.MESSAGE_LOCATION_NOT_FOUND);
        }
    }

    /**
     * @author lam
     * @return a list of all location
     */
    @Override
    public List<LocationModel> getAllLocations() {
        return locationRepository.findAll().stream().map(locationTransformer::apply).collect(Collectors.toList());
    }
}
