package com.gabojago.trip.route.controller;
import com.gabojago.trip.route.dto.TripPlaceDto;
import com.gabojago.trip.route.dto.TripRouteDto;
import com.gabojago.trip.route.service.TripRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/trip-routes")
public class TripRouteController {
    private TripRouteService tripRouteService;

    public TripRouteController(TripRouteService tripRouteService) {
        this.tripRouteService = tripRouteService;
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<TripRouteDto>> getAllUser(@PathVariable int userId) {
        log.debug("[GET] /trip-routes/");

        List<TripRouteDto> tripRouteDtoList = tripRouteService.getAllUser(userId);

        return new ResponseEntity<>(tripRouteDtoList, HttpStatus.OK);
    }
    @GetMapping("favorite/{routeId}")
    public ResponseEntity<?> getLikeRoute(@PathVariable int routeId) {
        log.debug("[GET] /trip-routes/favorite/"+routeId);

        tripRouteService.hitFavorite(routeId);

        return new ResponseEntity<>("route favorite 완료", HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TripRouteDto> get(@PathVariable int id) {
        log.debug("[GET] /trip-routes/" + id);

        TripRouteDto tripRouteDto = tripRouteService.get(id);

        return new ResponseEntity<>(tripRouteDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TripRouteDto>> get() {
        log.debug("[GET] /trip-routes/");

        List<TripRouteDto> tripRouteDtoList = tripRouteService.getAll();

        return new ResponseEntity<>(tripRouteDtoList, HttpStatus.OK);

    }

    @PostMapping//TODO: userID에 따른 TripRoute 받아야함
//    public ResponseEntity<ResponseDto> create(@RequestBody TripRouteDto tripRouteDto) {
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payLoad) {
        Map params = (Map) payLoad.get("params");
        log.debug("[POST] /trip-routes/");

        // set user information from the token HERE
        String testUrl = String.valueOf(params.get("trip_img_url"));
        int userId = Integer.parseInt(String.valueOf(params.get("user_id")));

        // create routeDto based on the request
        TripRouteDto tripRouteDto = new TripRouteDto();
        tripRouteDto.setTripImgUrl(testUrl);
        tripRouteDto.setUserId(userId);
        tripRouteDto.setName((String) params.get("name"));
        tripRouteService.create(tripRouteDto);

        // based on created route, create specific trip places
        int routeId = tripRouteService.getRecentRouteId(userId);
        List places = (List) params.get("tripPlaces");

        for (int i=0; i<places.size(); ++i) {
            Map placeParam = (Map) places.get(i);

            TripPlaceDto tripPlaceDto = new TripPlaceDto();
            tripPlaceDto.setTripRouteId(routeId);
            tripPlaceDto.setPlaceId(Integer.parseInt(String.valueOf(placeParam.get("placeId"))));
            tripPlaceDto.setTripDay(Integer.parseInt(String.valueOf(placeParam.get("tripDay"))));
            tripPlaceDto.setTripOrder(Integer.parseInt(String.valueOf(placeParam.get("tripOrder"))));

            tripRouteService.createPlace(tripPlaceDto);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody TripRouteDto tripRouteDto) {
        log.debug("[PUT] /trip-routes/");

        tripRouteDto.setId(id);
        log.debug(tripRouteDto.toString());

        String testUrl = "mod example url path here";
        int testUserId = 3;

        tripRouteDto.setTripImgUrl(testUrl);
        tripRouteDto.setUserId(testUserId);

        tripRouteService.update(tripRouteDto);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        log.debug("[DELETE] /trip-routes/" + id);

        tripRouteService.delete(id);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
