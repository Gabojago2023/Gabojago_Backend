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

    @GetMapping
    public ResponseEntity<?> getPlan(@RequestHeader("Authorization") String token,@RequestParam Integer placeId){
        List<TripRouteResDto> tripRoute = tripRouteService.getTripRouteByPlaceId(placeId);
        return ResponseEntity.status(HttpStatus.OK).body(tripRoute);
    }
    @PostMapping
    public ResponseEntity<?> addPlan(@RequestHeader("Authorization") String token, @RequestBody TripRouteCreateDto tripRouteCreateDto) {
        Integer userId = authService.getUserIdFromToken(token);
        tripRouteService.add(userService.getUser(userId), tripRouteCreateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{planId}")
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

    @PutMapping("/{planId}")
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
    @GetMapping("/{planId}")
    public ResponseEntity<?> searchPlan(@PathVariable Integer planId){
        TripRoute tripRoute =  tripRouteService.getTripRouteById(planId);
        TripRouteResDto tripRouteResDto = TripRouteResDto.from(tripRoute);
        return ResponseEntity.status(HttpStatus.OK).body(tripRouteResDto);
    }
    @GetMapping("/my")
    public ResponseEntity<?> getMyPlan(@RequestHeader("Authorization") String token){
        Integer userId = authService.getUserIdFromToken(token);
        User user = userService.getUser(userId);
        List<TripRoute> tripRouteList = tripRouteService.getMyTripRoutes(user);
        return ResponseEntity.status(HttpStatus.OK).body(tripRouteList);
    }
    private boolean isCompanionAndHasRole(User user, List<Companion> companionList) {
        return companionList.stream().filter(x -> x.getId() == user.getId())
                .filter(x -> x.getGrant() == CompanionGrant.WRITER)
                .count() == 1;
    }
}
