package com.gabojago.trip.route.service;

import com.gabojago.trip.route.dto.TripPlaceDto;
import com.gabojago.trip.route.dto.TripRouteCreateDto;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "local")
class TripRouteServiceImplTest {

    private final UserRepository userRepository;
    private final TripRouteService tripRouteService;

    @Autowired
    public TripRouteServiceImplTest(TripRouteService tripRouteService,
            UserRepository userRepository) {
        this.tripRouteService = tripRouteService;
        this.userRepository = userRepository;
    }

    @Test
    void add() {
        Optional<User> byId = userRepository.findById(6);
        TripRouteCreateDto tripRouteCreateDto = new TripRouteCreateDto();
        List<TripPlaceDto> tripPlaces = new ArrayList<>();
        TripPlaceDto tripPlaceDto = new TripPlaceDto();
        tripPlaceDto.setTripOrder(1);
        tripPlaceDto.setTripDay(1);
        tripPlaceDto.setPlaceId(127419);

        tripPlaces.add(tripPlaceDto);
        tripRouteCreateDto.setTripPlaces(tripPlaces);
        tripRouteService.add(byId.get(), tripRouteCreateDto);
    }
}