package com.gabojago.trip.place.controller;

import com.gabojago.trip.common.dto.ResponseDto;
import com.gabojago.trip.place.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {
    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getPlaceAll(){

        return new ResponseEntity<>(new ResponseDto(200, "완료", null), HttpStatus.OK);
    }
}
