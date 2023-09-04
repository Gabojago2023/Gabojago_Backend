package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.PlaceDetailResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.dto.response.RandomImageResponseDto;
import com.gabojago.trip.place.dto.response.SidoResponseDto;
import java.util.List;
import org.springframework.data.domain.Slice;

public interface PlaceService {

    List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode,
            Integer gugunCode,
            String keyword, Integer pg, Integer spp);

    List<PlaceResponseDto> searchTop3ScrappedPlaces(Integer top);

    Slice<PlaceResponseDto> searchAttractionByLocation(Integer userId, String location,
            Integer cursor,
            Integer size);

    Slice<PlaceResponseDto> getBookmarkedAttractionsByUserId(Integer userId, Integer cursor,
            Integer size);

    PlaceDetailResponseDto getPlaceDetailByPlaceId(Integer placeId);

    void addScrapPlace(Integer placeId, Integer userId);

    void removeScrapPlace(Integer placeId, Integer userId);

    List<RandomImageResponseDto> getRandomImages();

    List<SidoResponseDto> getSidoList();
}
