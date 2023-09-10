package com.gabojago.trip.place.controller;

import com.gabojago.trip.place.dto.request.CommentWithRatingDto;
import com.gabojago.trip.place.dto.response.CommentResponseDto;
import com.gabojago.trip.place.dto.response.GugunResponseDto;
import com.gabojago.trip.place.dto.response.PlaceDetailResponseDto;
import com.gabojago.trip.place.dto.response.PlaceResponseDto;
import com.gabojago.trip.place.dto.response.RandomImageResponseDto;
import com.gabojago.trip.place.dto.response.SidoResponseDto;
import com.gabojago.trip.place.service.CommentService;
import com.gabojago.trip.place.service.PlaceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
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

@Slf4j
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
    @GetMapping("/gugun/{sidoId}")
    public ResponseEntity<List<GugunResponseDto>> gugun(@PathVariable Integer sidoId) {
        return new ResponseEntity<>(placeService.getGugunInSido(sidoId), HttpStatus.OK);
    }
    @GetMapping("/keyword")
    public ResponseEntity<?> getPlaceSearchedByKeyword(@RequestParam("sido-code") Integer sidoCode,
            @RequestParam("gugun-code") Integer gugunCode, @RequestParam String keyword,
            @RequestParam(required = false) Integer cursor,
            @RequestParam Integer size) {
        // 북마크 여부 상관없이
        // refactoring 1순위
        Integer userId = 0;

        Map<String, Object> result = new HashMap<>();

        Slice<PlaceResponseDto> list = placeService.searchAttractionByKeyword(userId, sidoCode,
                gugunCode, keyword, cursor, size);

        Boolean hasNext = list.hasNext();

        result.put("places", list.getContent());
        result.put("hasNext", hasNext);
        Integer nextCursor =
                hasNext ? list.getContent().get(size - 1).getId() : null;
        result.put("nextCursor", nextCursor);

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
            @RequestParam(required = false) Integer cursor,
            @RequestParam Integer size) {
        // 북마크 여부 상관없이
        // refactoring 1순위
        Integer userId = 0;

        Map<String, Object> result = new HashMap<>();

        Slice<PlaceResponseDto> placeResponseDtos = placeService.searchAttractionByLocation(userId,
                location, cursor, size);

        Boolean hasNext = placeResponseDtos.hasNext();

        result.put("places", placeResponseDtos.getContent());
        result.put("hasNext", hasNext);
        Integer nextCursor =
                hasNext ? placeResponseDtos.getContent().get(size - 1).getId() : null;
        result.put("nextCursor", nextCursor);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/scrap")
    public ResponseEntity<?> getBookmarkedPlacesByUserId(
            @RequestParam(required = false) Integer cursor,
            @RequestParam Integer size,
            HttpServletRequest request) {
        // JWT토큰에서 파싱한 유저 id
        // 토큰 정보 없으면 필터 or 인터셉터 에서 401 반환
        Integer userId = (Integer) request.getAttribute("userId");
        log.debug("유저 id :" + userId);
        // 임의의 유저(테스트용)
        // userId = 1;

        Map<String, Object> result = new HashMap<>();

        Slice<PlaceResponseDto> bookmarkedAttractionsByUserId = placeService.getBookmarkedAttractionsByUserId(
                userId, cursor, size);

        Boolean hasNext = bookmarkedAttractionsByUserId.hasNext();

        result.put("places", bookmarkedAttractionsByUserId.getContent());
        result.put("hasNext", hasNext);
        Integer nextCursor =
                hasNext ? bookmarkedAttractionsByUserId.getContent().get(size - 1).getPsId() : null;
        result.put("nextCursor", nextCursor);

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

    @GetMapping("{placeId}/comments")
    public ResponseEntity<?> getCommentsByPlaceId(@PathVariable Integer placeId,
            @RequestParam(required = false) Integer cursor,
            @RequestParam Integer size) {
        Map<String, Object> result = new HashMap<>();

        Slice<CommentResponseDto> commentsByPlaceId = commentService.getCommentsByPlaceId(placeId,
                cursor, size);

        Boolean hasNext = commentsByPlaceId.hasNext();
        result.put("comments", commentsByPlaceId.getContent());
        result.put("hasNext", hasNext);

        Integer nextCursor = hasNext ? commentsByPlaceId.getContent().get(size - 1).getId() : null;
        result.put("nextCursor", nextCursor);
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

    @GetMapping("random-images")
    public ResponseEntity<?> getRandomImages() {
        List<RandomImageResponseDto> randomImages = placeService.getRandomImages();

        List<String> urls = randomImages.stream()
                .map(RandomImageResponseDto::getImgUrl)
                .collect(Collectors.toList());

        Map<String, List> result = new HashMap<>();
        result.put("images", urls);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("sido-list")
    public ResponseEntity<?> getSidoList() {
        List<SidoResponseDto> sidoList = placeService.getSidoList();
        return new ResponseEntity<>(sidoList, HttpStatus.OK);
    }
}
