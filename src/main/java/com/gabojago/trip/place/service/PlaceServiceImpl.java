package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, Integer sidoCode,
            Integer gugunCode, String keyword, Integer pg, Integer spp) {
        PageRequest pageRequest = PageRequest.of(pg - 1, spp);

        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Object[]> placesByFilter = placeRepository.findPlacesByFilter(userId, sidoCode,
                gugunCode, keyword, pageRequest);
        for (Object[] o : placesByFilter) {
            PlaceResponseDto from = PlaceResponseDto.from(o);
            placeResponseDtoList.add(from);
        }
        return placeResponseDtoList;
    }
}
