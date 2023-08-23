package com.gabojago.trip.ticket.service;

import com.gabojago.trip.ticket.domain.Ticket;

public interface TicketService {
    // 티켓 하나 삭제
    void useTicket(Integer ticketId);

    // 티켓 N개 조회
    Ticket[] findTicketsByUserId(Integer userId);

    // 티켓 하나 추가
    void addTicket(Integer userId);

    // 티켓을 다시 활성화 (문제 생긴 경우)
    void revertTicket(Ticket ticket);

    // 티켓 개수 조회
    Integer countTicketsByUserId(Integer userId);

}
