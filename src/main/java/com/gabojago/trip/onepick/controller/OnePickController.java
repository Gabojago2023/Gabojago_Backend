package com.gabojago.trip.onepick.controller;

import com.gabojago.trip.auth.service.AuthService;
import com.gabojago.trip.onepick.domain.OnePick;
import com.gabojago.trip.onepick.dto.OnePickDto;
import com.gabojago.trip.onepick.service.OnePickService;
import com.gabojago.trip.ticket.domain.Ticket;
import com.gabojago.trip.ticket.service.TicketService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 티켓 소비해서 원픽 뽑기
    @GetMapping("/random")
//    public ResponseEntity<OnePickDto> getOnePick(@RequestHeader("Authorization") String token, @RequestParam(required = false) String category, @RequestParam(required = false) String location) {
    public ResponseEntity<OnePickDto> getOnePick(@RequestParam(required = false) String category, @RequestParam(required = false) String location) {
        log.debug("[GET] /one-pick/random", category, location);

        // check if logged in
//        Integer userId = authService.getUserIdFromToken(token);
        Integer userId = 2;

        // check if ticket exists
        Integer ticketCount = ticketService.countTicketsByUserId(userId);
        if (ticketCount == 0) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // get tickets
        List<Ticket> tickets = List.of(ticketService.findTicketsByUserId(userId));

        // use ticket
        // should be atomic
        ticketService.useTicket(tickets.get(0).getId());
//
//        // get one pick
        OnePick onePick = onePickService.getOnePick(category, location, userId);


        return new ResponseEntity<>(OnePickDto.from(onePick), HttpStatus.OK);

    }
}
