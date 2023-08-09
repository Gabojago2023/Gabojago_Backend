package com.gabojago.trip.ticket.service;

import com.gabojago.trip.ticket.domain.Ticket;
import com.gabojago.trip.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void useTicket(Integer ticketId) {
        ticketRepository.useTicket(ticketId);
    }

    @Override
    public Ticket[] findTicketsByUserId(Integer userId, Integer count) {
        return ticketRepository.findTicketsByUserId(userId, count);
    }

    @Override
    public void addTicket(Integer userId) {
        ticketRepository.addTicket(userId);
    }

    @Override
    public Integer countTicketsByUserId(Integer userId) {
        return ticketRepository.countTicketsByUserId(userId);
    }

}
