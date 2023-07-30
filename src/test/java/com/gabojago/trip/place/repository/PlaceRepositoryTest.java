package com.gabojago.trip.place.repository;

import static org.assertj.core.api.Assertions.*;

import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@Transactional
class PlaceRepositoryTest {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void testGetPlaceById() {
        Optional<Place> byId = placeRepository.findById(125266);
        Place place = byId.get();
        assertThat(place.getName()).isEqualTo("국립 청태산자연휴양림");
    }

    @Test
    public void testfindTop3ScrappedPlacesId() {
        PageRequest pageRequest = PageRequest.of(0, 3);

        List<Integer> top3ScrappedPlacesId = placeRepository.findTop3ScrappedPlacesId(pageRequest);
        for (Integer placeId : top3ScrappedPlacesId) {
            Optional<Place> optionalValue = placeRepository.findById(placeId);
            if (optionalValue.isPresent()) {
                Place place = optionalValue.get();
                System.out.println("Place ID: " + place.getId() + ", Name: " + place.getName()
                        + ", Image URL: " + place.getImgUrl());
            }
        }
    }

    @Test
    void findPlacesByFilter() {
        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Object[]> placesByFilter = placeRepository.findPlacesByFilter(3, 1, 1, "공원");
        for(Object[] o : placesByFilter) {
            PlaceResponseDto from = PlaceResponseDto.from(o);
            placeResponseDtoList.add(from);
            System.out.println(from);
        }
    }
}