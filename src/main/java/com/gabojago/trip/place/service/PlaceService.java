package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;

import java.util.List;

public interface PlaceService {

    List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode, Integer gugunCode,
            String keyword, Integer pg, Integer spp);

    List<PlaceResponseDto> searchTop3ScrappedPlaces(Integer top);
}
