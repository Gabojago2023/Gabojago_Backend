package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.domain.PlaceScrap;
import com.gabojago.trip.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceScrapRepository extends JpaRepository<PlaceScrap, Long> {

    Optional<PlaceScrap> findByPlaceIdAndUserId(Integer placeId, Integer userId);

    void deleteByPlaceAndUser(Place place, User user);
}
