package com.gabojago.trip.place.controller;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.service.PlaceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<?> getPlaceAll() {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getPlaceSearchedByKeyword(@RequestParam("sido-code") Integer sidoCode,
            @RequestParam("gugun-code") Integer gugunCode,
            @RequestParam String keyword) {
        // 임의의 userId
        Integer userId = 3;
        Map<String, List> result = new HashMap<>();
        try {
            List<PlaceResponseDto> list = placeService.searchAttractionByKeyword(userId, sidoCode,
                    gugunCode, keyword);
            result.put("places", list);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("검색 실패!!!", HttpStatus.NOT_ACCEPTABLE);
        }
//        try {
//            List<PlaceResponseDto> list = placeService.searchAttractionByKeyword(Integer.parseInt(sidoCode),
//                    Integer.parseInt(gugunCode), keyword);
//            return new ResponseEntity<List>(list, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<String>("검색 실패!!!", HttpStatus.NOT_ACCEPTABLE);
//        }
    }
}
