package com.gabojago.trip.onepick.controller;

import com.gabojago.trip.auth.service.AuthService;
import com.gabojago.trip.onepick.domain.DistributedOnePick;
import com.gabojago.trip.onepick.domain.OnePick;
import com.gabojago.trip.onepick.dto.DistributedOnePickDto;
import com.gabojago.trip.onepick.dto.DistributedRateDto;
import com.gabojago.trip.onepick.dto.OnePickDto;
import com.gabojago.trip.onepick.dto.RankedOnePickDto;
import com.gabojago.trip.onepick.service.OnePickService;
import com.gabojago.trip.ticket.domain.Ticket;
import com.gabojago.trip.ticket.service.TicketService;
import com.gabojago.trip.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/one-pick")
public class OnePickController {

    private final OnePickService onePickService;
    private final TicketService ticketService;
    private final AuthService authService;

    // 나의 모든 원픽 장소 조회
    @GetMapping
    public ResponseEntity<List<OnePickDto>> getAllOnePicks(@RequestHeader("Authorization") String token) {
        Integer userId = authService.getUserIdFromToken(token);

        List<OnePick> allOnePicks = onePickService.getAllValidOnePicksByUserId(userId);

        List<OnePickDto> onePickDtos = new ArrayList<>();
        for (OnePick onePick : allOnePicks) {
            onePickDtos.add(OnePickDto.from(onePick));
        }

        return new ResponseEntity<>(onePickDtos, HttpStatus.OK);
    }


    // 원픽 장소 등록
    @PostMapping
    public ResponseEntity<?> registerOnePick(@RequestHeader("Authorization") String token, @RequestBody OnePickDto onePickDto) {
        log.debug("[POST] /one-pick", onePickDto);

        // check if logged in
        Integer userId = authService.getUserIdFromToken(token);

        // register one pick
        try {
            onePickService.addOnePick(OnePick.from(onePickDto, new User(userId)));
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (Exception e) {
            // non-existing place id
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 나의 원픽 업데이트
    @PutMapping
    public ResponseEntity<?> updateOnePick(@RequestHeader("Authorization") String token, @RequestBody OnePickDto onePickDto) {
        log.debug("[PUT] /one-pick", onePickDto);

        // check if logged in
        Integer userId = authService.getUserIdFromToken(token);

        // register one pick
        try {
            onePickService.addOnePick(OnePick.from(onePickDto, new User(userId)));
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (Exception e) {
            // non-existing place id
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 티켓 소비해서 원픽 뽑기
    @GetMapping("/random")
    public ResponseEntity<OnePickDto> getOnePick(@RequestHeader("Authorization") String token, @RequestParam(required = false) Integer category, @RequestParam(required = false) Integer location) {
        log.debug("[GET] /one-pick/random", category, location);

        // check if logged in
        Integer userId = authService.getUserIdFromToken(token);

        // check if ticket exists
        Integer ticketCount = ticketService.countTicketsByUserId(userId);
        if (ticketCount == 0)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Ticket> tickets = List.of(ticketService.findTicketsByUserId(userId));
        Ticket ticketToUse = tickets.get(0);

        // atomic transaction
        try {
            ticketService.useTicket(tickets.get(0).getId());

            OnePick onePick = onePickService.getOnePick(category, location, userId);
            return new ResponseEntity<>(OnePickDto.from(onePick), HttpStatus.OK);

        } catch (Exception e) {
            ticketService.revertTicket(ticketToUse);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/random-all")
    public ResponseEntity<List<DistributedOnePickDto>> getDistributedOnePick(@RequestHeader("Authorization") String token) {
        Integer userId = authService.getUserIdFromToken(token);

        List<DistributedOnePick> allRandoms = onePickService.getAllDistributedOnePick(userId);
        List<DistributedOnePickDto> response = new ArrayList<>();
        for (DistributedOnePick distributedOnePick : allRandoms) {
            response.add(DistributedOnePickDto.from(distributedOnePick));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 뽑은 원픽 평가 점수 수정
    @PutMapping("/random")
    public ResponseEntity<?> rateOnePick(@RequestHeader("Authorization") String token, @RequestBody DistributedRateDto distributedRateDto) {
        log.debug("[PUT] /one-pick/rate", distributedRateDto);
        Integer userId = authService.getUserIdFromToken(token);

        try {
            onePickService.rateDistributedOnePick(distributedRateDto);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 내가 뽑은 원픽 좋아요
    @PostMapping("/like")
    public ResponseEntity<?> likeOnePick(@RequestHeader("Authorization") String token, @RequestParam("id") Integer distributedOnePickId) {
        log.debug("[POST] /one-pick/like", distributedOnePickId);
        Integer userId = authService.getUserIdFromToken(token);

        try {
            onePickService.likeDistributedOnePick(distributedOnePickId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 카테고리 별 좋아요를 가장 많이 받은 원픽 조회
    @GetMapping("/rank")
    public ResponseEntity<RankedOnePickDto> getRanking(@RequestParam("category") Integer category) {

        RankedOnePickDto response = onePickService.getMostLikedOnePick(category);
        if (response == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}