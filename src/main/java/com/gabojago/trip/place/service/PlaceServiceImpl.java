package com.gabojago.trip.place.service;

import com.gabojago.trip.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService{
    private PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }
}
