package com.gabojago.trip.route.service;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.repository.PlaceRepository;
import com.gabojago.trip.route.domain.Companion;
import com.gabojago.trip.route.domain.TripPlace;
import com.gabojago.trip.route.domain.TripRoute;
import com.gabojago.trip.route.dto.TripPlaceDto;
import com.gabojago.trip.route.dto.TripRouteCreateDto;
import com.gabojago.trip.route.dto.TripRouteModifyDto;
import com.gabojago.trip.route.dto.TripRouteResDto;
import com.gabojago.trip.route.exception.TripRouteNotFoundException;
import com.gabojago.trip.route.repository.CompanionRepository;
import com.gabojago.trip.route.repository.TripPlaceRepository;
import com.gabojago.trip.route.repository.TripRouteRepository;
import com.gabojago.trip.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TripRouteServiceImpl implements TripRouteService {

    private final TripRouteRepository tripRouteRepository;
    private final CompanionRepository companionRepository;

    private final TripPlaceRepository tripPlaceRepository;

    private final PlaceRepository placeRepository;

    @Override
    public void add(User user, TripRouteCreateDto tripRouteCreateDto) {
        TripRoute savedTripRoute = tripRouteRepository.save(
                TripRoute.from(tripRouteCreateDto, user));

        // 장소_id, 여행일자, 여행 순서 차례대로 저장
        List<TripPlaceDto> tripPlaces = tripRouteCreateDto.getTripPlaces();
        for (TripPlaceDto tripPlaceDto : tripPlaces) {
            Optional<Place> byId = placeRepository.findById(tripPlaceDto.getPlace().getId());
            if (byId.isPresent()) {
                Place place = byId.get();
                tripPlaceRepository.save(TripPlace.from(tripPlaceDto, savedTripRoute, place));
            }
        }
    }

    @Override
    public User getOwner(Integer tripRouteId) {
        return tripRouteRepository.findUserByTripRouteId(tripRouteId);
    }

    @Override
    public void delete(Integer planId) {
        TripRoute tripRoute = tripRouteRepository.findById(planId)
                .orElseThrow(() -> new TripRouteNotFoundException("triproute에 헤당하는 id 없음"));
        tripRouteRepository.delete(tripRoute);
    }

    @Override
    public List<Companion> getCompanions(Integer planId) {
        return companionRepository.findAllByTripRouteId(planId);
    }

    @Override
    public void edit(TripRouteModifyDto tripRouteModifyDto) {
        //TODO : 동행자 추가 삭제
        TripRoute tripRoute = tripRouteRepository.findById(tripRouteModifyDto.getId())
                .orElseThrow(() -> new TripRouteNotFoundException("triprouteId 없음"));
        TripRoute updated = TripRoute.from(tripRouteModifyDto, tripRoute.getUser());
        tripRouteRepository.save(updated);
    }

    @Override
    public TripRoute getTripRouteById(Integer tripRouteId) {
        TripRoute tripRoute = tripRouteRepository.findById(tripRouteId)
                .orElseThrow(() -> new TripRouteNotFoundException("tripRoute 없음"));
        return tripRoute;
    }

    @Override
    public List<TripRoute> getMyTripRoutes(User user) {
        List<TripRoute> tripRoutes =
                tripRouteRepository.findByUser(user.getId());

        return tripRoutes;
    }

    @Override
    public List<TripRouteResDto> getTripRouteByPlaceId(Integer placeId, Integer userId) {
        List<TripRouteResDto> tripRoutes =
                tripRouteRepository.findByPlaceId(placeId)
                        .stream().filter(x -> x.getUser().getId() == userId || x.isPublic() == true
                                || isCompanion(x.getId(), userId))
                        .map(TripRouteResDto::from)
                        .toList();

        return tripRoutes;
    }

    private boolean isCompanion(Integer tripRouteId, Integer userId) {
        List<Companion> companions = getCompanions(tripRouteId);
        for (Companion companion : companions) {
            if (companion.getCreator().getId() == userId) {
                return true;
            }
        }
        return false;
    }
}
