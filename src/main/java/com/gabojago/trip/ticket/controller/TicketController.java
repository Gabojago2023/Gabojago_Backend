package com.gabojago.trip.ticket.controller;

import com.gabojago.trip.auth.service.AuthService;
import com.gabojago.trip.ticket.service.TicketService;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final AuthService authService;

    public TicketController(TicketService ticketService, AuthService authService) {
        this.ticketService = ticketService;
        this.authService = authService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestHeader("Authorization") String token) {
        Integer userId = authService.getUserIdFromToken(token);

        log.debug("[POST] /ticket/purchase " + userId);
        // 여기서 추가적인 결제 인증 로직이 필요할 수 있음
        ticketService.addTicket(userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTicketCount(@RequestHeader("Authorization") String token) {
        Integer userId = authService.getUserIdFromToken(token);

        log.debug("[GET] /ticket/count/" + userId);
        Integer count = ticketService.countTicketsByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
