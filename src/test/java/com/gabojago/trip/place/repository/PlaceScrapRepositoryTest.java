package com.gabojago.trip.place.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.domain.PlaceScrap;
import com.gabojago.trip.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@Transactional()
//@Rollback(value = false)
class PlaceScrapRepositoryTest {

    private final PlaceScrapRepository placeScrapRepository;

    @Autowired
    public PlaceScrapRepositoryTest(PlaceScrapRepository placeScrapRepository) {
        this.placeScrapRepository = placeScrapRepository;
    }

    @Test
    public void testAddScrapPlace() {
        // Given
        Integer placeId = 126485;
        Integer userId = 1;

        // When
        Place place = new Place(placeId);
        User user = new User(userId);
        PlaceScrap placeScrap = new PlaceScrap(place, user);
        placeScrapRepository.save(placeScrap);

        // Then
        PlaceScrap savedPlaceScrap = placeScrapRepository.findByPlaceIdAndUserId(placeId, userId);
        assertNotNull(savedPlaceScrap);
        assertEquals(placeId, savedPlaceScrap.getPlace().getId());
        assertEquals(userId, savedPlaceScrap.getUser().getId());
    }

    @Test
    void testDeleteByPlaceAndUser() {
        // Given
        Integer placeId = 126485;
        Integer userId = 1;

        // When
        Place place = new Place(placeId);
        User user = new User(userId);
        placeScrapRepository.deleteByPlaceAndUser(place, user);
    }
}