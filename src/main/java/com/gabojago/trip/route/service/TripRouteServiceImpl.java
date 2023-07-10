package com.gabojago.trip.route.service;

import com.gabojago.trip.route.dto.TripPlaceDto;
import com.gabojago.trip.route.dto.TripRouteDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TripRouteServiceImpl implements TripRouteService{

    @Override
    public List<TripRouteDto> getAllUser(int userId) {
        return null;
    }

    @Override
    public void hitFavorite(int routeId) {

    }

    @Override
    public List<TripRouteDto> getAll() {
        return null;
    }

    @Override
    public int getRecentRouteId(int userId) {
        return 0;
    }

    @Override
    public TripRouteDto get(int id) {
        return null;
    }

    @Override
    public void create(TripRouteDto tripRouteDto) {

    }

    @Override
    public void createPlace(TripPlaceDto tripPlaceDto) {

    }

    @Override
    public void update(TripRouteDto tripRouteDto) {

    }

    @Override
    public void delete(int id) {

    }
}
