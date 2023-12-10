package com.gabojago.trip.route.controller;

import com.gabojago.trip.auth.exception.UserUnAuthorizedException;
import com.gabojago.trip.auth.service.AuthService;
import com.gabojago.trip.route.domain.Companion;
import com.gabojago.trip.route.domain.CompanionGrant;
import com.gabojago.trip.route.domain.TripRoute;
import com.gabojago.trip.route.dto.TripRouteCreateDto;
import com.gabojago.trip.route.dto.TripRouteModifyDto;
import com.gabojago.trip.route.dto.TripRouteResDto;
import com.gabojago.trip.route.service.TripRouteService;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class TripRouteController {
    private final TripRouteService tripRouteService;
    private final AuthService authService;
    private final UserService userService;

    @GetMapping // 해당 장소가 포함된 여행 계획 목록 조회 -> 볼 수 있는 권한이 있는지 확인해야 함
    public ResponseEntity<?> getPlanContainPlace(@RequestHeader("Authorization") String token,@RequestParam Integer placeId){
        User owner = userService.getUser(authService.getUserIdFromToken(token));

        List<TripRouteResDto> tripRoute = tripRouteService.getTripRouteByPlaceId(placeId,owner.getId());

        return ResponseEntity.status(HttpStatus.OK).body(tripRoute);
    }
    @PostMapping // 여행 계획 등록
    public ResponseEntity<?> addPlan(@RequestHeader("Authorization") String token, @RequestBody TripRouteCreateDto tripRouteCreateDto) {
        Integer userId = authService.getUserIdFromToken(token);
        tripRouteService.add(userService.getUser(userId), tripRouteCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{planId}") // 여행 계획 삭제
    public ResponseEntity<?> deletePlan(@RequestHeader("Authorization") String token, @PathVariable Integer planId) {
        Integer userId = authService.getUserIdFromToken(token);
        User user = userService.getUser(userId);
        User creator = tripRouteService.getOwner(planId);
        if (creator.getId() != user.getId()) {
            throw new UserUnAuthorizedException("잘못된 권한입니다.");
        }
        tripRouteService.delete(planId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{planId}") // 여행 계획 수정
    public ResponseEntity<?> editPlan(@RequestHeader("Authorization") String token,
                                      @PathVariable Integer planId,
                                      @RequestBody TripRouteModifyDto tripRouteModifyDto) {
        Integer userId = authService.getUserIdFromToken(token);
        User user = userService.getUser(userId);

        User owner = tripRouteService.getOwner(planId);
        List<Companion> companion = tripRouteService.getCompanions(planId);
        if (user.getId() != owner.getId() && !isCompanionAndHasRole(user, companion)) {
            throw new UserUnAuthorizedException("권한 없음");
        }
        tripRouteService.edit(tripRouteModifyDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{planId}") // 특정 여행 계획 조회
    public ResponseEntity<?> searchPlan(@PathVariable Integer planId){
        TripRoute tripRoute =  tripRouteService.getTripRouteById(planId);
        TripRouteResDto tripRouteResDto = TripRouteResDto.from(tripRoute);
        return ResponseEntity.status(HttpStatus.OK).body(tripRouteResDto);
    }
    @GetMapping("/my") // 내 여행 계획 목록 조회 (내가 생성자, 참여자)
    public ResponseEntity<?> getMyPlan(@RequestHeader("Authorization") String token){
        Integer userId = authService.getUserIdFromToken(token);
        User user = userService.getUser(userId);
        List<TripRouteResDto> tripRouteList = tripRouteService.getMyTripRoutes(user).stream()
                .map(TripRouteResDto::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(tripRouteList);
    }
    private boolean isCompanionAndHasRole(User user, List<Companion> companionList) {
        return companionList.stream().filter(x -> x.getId() == user.getId())
                .filter(x -> x.getCompanionGrant() == CompanionGrant.WRITER)
                .count() == 1;
    }
}
