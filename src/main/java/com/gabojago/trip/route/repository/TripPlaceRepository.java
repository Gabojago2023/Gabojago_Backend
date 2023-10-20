package com.gabojago.trip.route.repository;

import com.gabojago.trip.route.domain.TripPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlaceRepository extends JpaRepository<TripPlace, Integer> {

}
