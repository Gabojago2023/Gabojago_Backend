package com.gabojago.trip.route.service;

import com.gabojago.trip.route.domain.Companion;
import com.gabojago.trip.route.domain.TripRoute;
import com.gabojago.trip.route.dto.TripRouteCreateDto;
import com.gabojago.trip.route.dto.TripRouteModifyDto;
import com.gabojago.trip.route.exception.TripRouteNotFoundException;
import com.gabojago.trip.route.repository.CompanionRepository;
import com.gabojago.trip.route.repository.TripRouteRepository;
import com.gabojago.trip.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TripRouteServiceImpl implements TripRouteService{
    private final TripRouteRepository tripRouteRepository;
    private final CompanionRepository companionRepository;
    @Override
    public void add(User user, TripRouteCreateDto tripRouteCreateDto) {
       tripRouteRepository.save(TripRoute.from(tripRouteCreateDto,user));
    }

    @Override
    public User getOwner(Integer tripRouteId) {
        return tripRouteRepository.findUserByTripRouteId(tripRouteId);
    }

    @Override
    public void delete(Integer planId) {
        TripRoute tripRoute = tripRouteRepository.findById(planId).orElseThrow(()-> new TripRouteNotFoundException("triproute에 헤당하는 id 없음"));
        tripRouteRepository.delete(tripRoute);
    }

    @Override
    public List<Companion> getCompanions(Integer planId) {
        return companionRepository.findCompanionsByTripRouteId(planId);
    }

    @Override
    public void edit(TripRouteModifyDto tripRouteModifyDto) {
        //TODO : 동행자 추가 삭제
        TripRoute tripRoute = tripRouteRepository.findById(tripRouteModifyDto.getId())
                .orElseThrow(()-> new TripRouteNotFoundException("triprouteId 없음"));
        TripRoute updated = TripRoute.from(tripRouteModifyDto,tripRoute.getUser());
        tripRouteRepository.save(updated);
    }

    @Override
    public TripRoute getTripRouteBy(Integer tripRouteId) {
        TripRoute tripRoute = tripRouteRepository.findById(tripRouteId)
                .orElseThrow(()->new TripRouteNotFoundException("tripRoute 없음"));
        return tripRoute;
    }

    @Override
    public List<TripRoute> getMyTripRoutes(User user) {
        List<TripRoute> tripRoutes =
                tripRouteRepository.findByUser(user.getId());
        return tripRoutes;
    }
}
