package com.gabojago.trip.route.repository;

import com.gabojago.trip.route.domain.TripRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRouteRepository extends JpaRepository<TripRoute,Long> {

}
