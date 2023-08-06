package com.gabojago.trip.ticket.repository;

import com.gabojago.trip.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // 티켓 한 개 소모 (type 0: 사용된 티켓)
    @Query("UPDATE Ticket t SET t.type = 0 WHERE t.id = :ticketId")
    void useTicket(Integer ticketId);

    // 유저가 보유중인 티켓을 N개 조회
    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId AND t.type = 1 LIMIT :count")
    Ticket[] findTicketsByUserId(Integer userId, Integer count);

    // 구매, 티켓 하나 추가
    @Query("INSERT INTO Ticket (user_id, type) VALUES (:userId, 1)")
    void addTicket(Integer userId);

    // 티켓 개수 조회
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.user.id = :userId AND t.type = 1")
    Integer countTicketsByUserId(Integer userId);

}
