package com.gabojago.trip.route.service;

import com.gabojago.trip.route.dto.TripPlaceDto;
import com.gabojago.trip.route.dto.TripRouteDto;
import java.util.List;

public interface TripRouteService {

    List<TripRouteDto> getAllUser(int userId);

    void hitFavorite(int routeId);

    List<TripRouteDto> getAll();

    int getRecentRouteId(int userId);

    TripRouteDto get(int id);

    void create(TripRouteDto tripRouteDto);

    void createPlace(TripPlaceDto tripPlaceDto);

    void update(TripRouteDto tripRouteDto);

    void delete(int id);
}
