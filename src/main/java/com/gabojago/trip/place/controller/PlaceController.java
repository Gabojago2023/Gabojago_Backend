package com.gabojago.trip.place.controller;

import com.gabojago.trip.place.dto.request.CommentWithRatingDto;
import com.gabojago.trip.place.dto.response.CommentResponseDto;
import com.gabojago.trip.place.dto.response.PlaceDetailResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.service.CommentService;
import com.gabojago.trip.place.service.PlaceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        // 북마크 여부 상관없이
        // refactoring 1순위
        Integer userId = 0;

        Map<String, List> result = new HashMap<>();
        List<PlaceResponseDto> list = placeService.searchAttractionByKeyword(userId, sidoCode,
                gugunCode, keyword, pg, spp);
        result.put("places", list);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
        // 북마크 여부 상관없이
        // refactoring 1순위
        Integer userId = 0;

        Map<String, List> result = new HashMap<>();
        List<PlaceResponseDto> placeResponseDtoList = placeService.searchAttractionByLocation(
                userId, location, pg, spp);

        result.put("places", placeResponseDtoList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/scrap")
    public ResponseEntity<?> getBookmarkedPlacesByUserId(@RequestParam Integer pg,
            @RequestParam Integer spp,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");

        // 임의의 유저(테스트용)
        userId = 1;

        Map<String, List> result = new HashMap<>();

        List<PlaceResponseDto> bookmarkedAttractionsByUserId = placeService.getBookmarkedAttractionsByUserId(
                userId, pg, spp);

        result.put("places", bookmarkedAttractionsByUserId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{placeId}/detail")
    public ResponseEntity<?> getPlaceDetail(@PathVariable Integer placeId) {
        Map<String, PlaceDetailResponseDto> result = new HashMap<>();
        PlaceDetailResponseDto placeDetailByPlaceId = placeService.getPlaceDetailByPlaceId(placeId);

        result.put("place", placeDetailByPlaceId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{placeId}/scrap")
    public ResponseEntity<?> addScrapPlace(@PathVariable Integer placeId,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");

        // 임의의 유저(테스트용)
        userId = 1;

        placeService.addScrapPlace(placeId, userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/{placeId}/scrap")
    public ResponseEntity<?> deleteScrapPlace(@PathVariable Integer placeId,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");

        // 임의의 유저(테스트용)
        userId = 1;

        placeService.removeScrapPlace(placeId, userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/{placeId}/comment")
    public ResponseEntity<?> addPlaceComment(@PathVariable Integer placeId,
            @RequestBody CommentWithRatingDto comment,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");

        // 임의의 유저(테스트용)
        userId = 1;

        commentService.addCommentToPlace(placeId, userId, comment.getComment(), comment.getRate());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("{placeId}/comment")
    public ResponseEntity<?> getCommentsByPlaceId(@PathVariable Integer placeId) {
        Map<String, List<CommentResponseDto>> result = new HashMap<>();

        List<CommentResponseDto> commentsByPlaceId = commentService.getCommentsByPlaceId(placeId);

        result.put("comments", commentsByPlaceId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("{placeId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer placeId,
            @PathVariable Integer commentId,
            @RequestBody CommentWithRatingDto comment,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");

        // 임의의 유저(테스트용. 지울예정)
        userId = 1;

        commentService.updateComment(userId, placeId, commentId, comment.getComment(),
                comment.getRate());
        return new ResponseEntity<>("", HttpStatus.OK);

    }

    @DeleteMapping("{placeId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer placeId,
            @PathVariable Integer commentId,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");

        // 임의의 유저(테스트용. 지울예정)
        userId = 1;

        commentService.deleteCommentById(userId, commentId);
        return new ResponseEntity<>("", HttpStatus.OK);

    }
}
