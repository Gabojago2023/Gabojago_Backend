package com.gabojago.trip.route.repository;

import com.gabojago.trip.route.domain.TripRoute;
import com.gabojago.trip.route.dto.TripRouteResDto;
import com.gabojago.trip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRouteRepository extends JpaRepository<TripRoute,Integer> {
    @Query("select tr.user from TripRoute tr where tr.id = :tripRouteId")
    User findUserByTripRouteId(Integer tripRouteId);

    @Query("select tr from TripRoute tr where tr.user.id = :userId")
    List<TripRoute> findByUser(Integer userId);
    @Query("select tr from TripPlace tp join TripRoute tr on tp.tripRoute.id = tr.id  " +
            "where tp.place = :placeId")
    List<TripRouteResDto> findByPlaceId(Integer placeId);

}
