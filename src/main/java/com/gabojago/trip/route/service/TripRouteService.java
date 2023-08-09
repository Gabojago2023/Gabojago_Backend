package com.gabojago.trip.route.service;

import com.gabojago.trip.route.domain.Companion;
import com.gabojago.trip.route.domain.TripRoute;
import com.gabojago.trip.route.dto.TripRouteCreateDto;
import com.gabojago.trip.route.dto.TripRouteModifyDto;
import com.gabojago.trip.route.dto.TripRouteResDto;
import com.gabojago.trip.user.domain.User;

import java.util.List;

public interface TripRouteService {


    void add(User user, TripRouteCreateDto tripRouteCreateDto);

    User getOwner(Integer tripRouteId);

    void delete(Integer planId);


    List<Companion> getCompanions(Integer planId);

    void edit(TripRouteModifyDto tripRouteModifyDto);

    TripRoute getTripRouteById(Integer tripRouteId);

    List<TripRoute> getMyTripRoutes(User user);

    List<TripRouteResDto> getTripRouteByPlaceId(Integer placeId);
}
