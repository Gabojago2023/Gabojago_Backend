package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;

import java.util.List;

public interface PlaceService {

    List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode, Integer gugunCode,
            String keyword);
}
