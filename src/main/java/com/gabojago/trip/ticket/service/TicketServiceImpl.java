package com.gabojago.trip.ticket.service;

import com.gabojago.trip.ticket.domain.Ticket;
import com.gabojago.trip.ticket.repository.TicketRepository;
import com.gabojago.trip.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void useTicket(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket != null) {
            ticket.setType(0);
            ticketRepository.save(ticket);
        }
    }

    @Override
    public Ticket[] findTicketsByUserId(Integer userId) {
        return ticketRepository.findTicketsByUserId(userId);
    }

    @Override
    public void addTicket(Integer userId) {
        User user = new User();
        user.setId(userId);
        // user not found exception handling required
        ticketRepository.save(new Ticket(user, 1));
    }

    @Override
    public void revertTicket(Ticket ticket) {
        ticket.setType(1);
        ticketRepository.save(ticket);
    }

    @Override
    public Integer countTicketsByUserId(Integer userId) {
        return ticketRepository.countTicketsByUserId(userId);
    }

}
