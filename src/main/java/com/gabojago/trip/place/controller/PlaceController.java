package com.gabojago.trip.place.controller;

import com.gabojago.trip.place.dto.request.CommentWithRatingDto;
import com.gabojago.trip.place.dto.response.PlaceDetailResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.service.CommentService;
import com.gabojago.trip.place.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final CommentService commentService;

    @Autowired
    public PlaceController(PlaceService placeService, CommentService commentService) {
        this.placeService = placeService;
        this.commentService = commentService;
    }

//    @GetMapping
//    public ResponseEntity<?> getPlaceAll() {
//
//        return new ResponseEntity<>("", HttpStatus.OK);
//    }

    @GetMapping("/keyword")
    public ResponseEntity<?> getPlaceSearchedByKeyword(@RequestParam("sido-code") Integer sidoCode,
            @RequestParam("gugun-code") Integer gugunCode, @RequestParam String keyword,
            @RequestParam Integer pg, @RequestParam Integer spp) {
        // 로그인 한 유저라면 그 유저의 id
        // 비로그인 시 0
        Integer userId = 0;
        Map<String, List> result = new HashMap<>();
        List<PlaceResponseDto> list = placeService.searchAttractionByKeyword(userId, sidoCode,
                gugunCode, keyword, pg, spp);
        result.put("places", list);
        if (list.size() != 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/like-place")
    public ResponseEntity<?> getTop3Places(@RequestParam("top") Integer top) {
        Map<String, List> result = new HashMap<>();
        List<PlaceResponseDto> placeResponseDtoList = placeService.searchTop3ScrappedPlaces(top);

        result.put("top3Places", placeResponseDtoList);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getPlaceSearchedByLocation(@RequestParam("location") String location,
            @RequestParam Integer pg, @RequestParam Integer spp) {
        // 로그인 한 유저라면 그 유저의 id
        // 비로그인 시 0
        Integer userId = 0;
        Map<String, List> result = new HashMap<>();
        List<PlaceResponseDto> placeResponseDtoList = placeService.searchAttractionByLocation(
                userId, location, pg, spp);

        result.put("places", placeResponseDtoList);
        if (placeResponseDtoList.size() != 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/scrap")
    public ResponseEntity<?> getBookmarkedPlacesByUserId(@RequestParam Integer pg,
            @RequestParam Integer spp) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = 1;
        Map<String, List> result = new HashMap<>();

        List<PlaceResponseDto> bookmarkedAttractionsByUserId = placeService.getBookmarkedAttractionsByUserId(
                userId, pg, spp);

        result.put("places", bookmarkedAttractionsByUserId);
        if (bookmarkedAttractionsByUserId.size() != 0) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{placeId}/detail")
    public ResponseEntity<?> getPlaceDetail(@PathVariable Integer placeId) {
        Map<String, PlaceDetailResponseDto> result = new HashMap<>();
        PlaceDetailResponseDto placeDetailByPlaceId = placeService.getPlaceDetailByPlaceId(placeId);
        if (placeDetailByPlaceId != null) {
            result.put("place", placeDetailByPlaceId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/{placeId}/scrap")
    public ResponseEntity<?> addScrapPlace(@PathVariable Integer placeId) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = 1;

        placeService.addScrapPlace(placeId, userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/{placeId}/scrap")
    public ResponseEntity<?> deleteScrapPlace(@PathVariable Integer placeId) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = 1;

        placeService.removeScrapPlace(placeId, userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/{placeId}/comment")
    public ResponseEntity<?> addPlaceComment(@PathVariable Integer placeId,
            @RequestBody CommentWithRatingDto comment) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환

        // 임의의 유저
        Integer userId = 1;

        commentService.addCommentToPlace(placeId, userId, comment.getComment(), comment.getRate());
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
