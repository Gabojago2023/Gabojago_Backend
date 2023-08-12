package com.gabojago.trip.place.controller;

import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam String keyword,
            @RequestParam Integer pg,
            @RequestParam Integer spp) {
        // 로그인 한 유저라면 그 유저의 id
        // 비로그인 시 -1
        Integer userId = -1;
        Map<String, List> result = new HashMap<>();
        List<PlaceResponseDto> list = placeService.searchAttractionByKeyword(userId, sidoCode,
                gugunCode, keyword, pg, spp);
        result.put("places", list);
        if (list.size() != 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

//        try {
//            List<PlaceResponseDto> list = placeService.findAttractionByKeyword(userId, sidoCode,
//                    gugunCode, keyword, pg, spp);
//            result.put("places", list);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("검색 실패!!!", HttpStatus.NOT_ACCEPTABLE);
//        }
    }
}
