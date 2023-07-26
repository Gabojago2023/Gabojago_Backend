package com.gabojago.trip.place.service;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService{
    private PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public List<PlaceResponseDto> searchAttractionByKeyword(Integer userId, String sido,
            String gugun, String keyword) {
        List<PlaceResponseDto> placeResponseDtoList = new ArrayList<>();
        List<Object[]> placesByFilter = placeRepository.findPlacesByFilter(1, "서울", "강남", "공원");
        for(Object[] o : placesByFilter) {
            PlaceResponseDto from = PlaceResponseDto.from(o);
            placeResponseDtoList.add(from);
            System.out.println(from);
        }
        return placeResponseDtoList;
    }
}
