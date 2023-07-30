package com.gabojago.trip.place.controller;

import com.gabojago.trip.common.dto.ResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.service.PlaceService;
import java.util.List;
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

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getPlaceAll() {

        return new ResponseEntity<>(new ResponseDto(200, "완료", null), HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getPlaceSearchedByKeyword(@RequestParam Integer sidoCode,
            @RequestParam Integer gugunCode,
            @RequestParam String keyword) {
        // 임의의 userId
        Integer userId = 3;
        try {
            List<PlaceResponseDto> list = placeService.searchAttractionByKeyword(userId, sidoCode,
                    gugunCode, keyword);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("검색 실패!!!", HttpStatus.NOT_ACCEPTABLE);
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
