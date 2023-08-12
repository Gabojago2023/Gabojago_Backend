package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import java.util.List;

public interface PlaceService {

    List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode,
            Integer gugunCode,
            String keyword, Integer pg, Integer spp);

    List<PlaceResponseDto> searchTop3ScrappedPlaces(Integer top);

    List<PlaceResponseDto> searchAttractionByLocation(Integer userId, String location, Integer pg,
            Integer spp);

    List<PlaceResponseDto> getBookmarkedAttractionsByUserId(Integer userId, Integer pg,
            Integer spp);
}
