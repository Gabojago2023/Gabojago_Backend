package com.gabojago.trip.route.repository;

import com.gabojago.trip.route.domain.Companion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanionRepository extends JpaRepository<Companion,Integer> {
    List<Companion> findAllByTripRouteId(Integer planId);

}
