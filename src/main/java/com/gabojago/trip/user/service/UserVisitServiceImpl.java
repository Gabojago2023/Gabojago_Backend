package com.gabojago.trip.user.service;

import com.gabojago.trip.ticket.service.TicketService;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserVisit;
import com.gabojago.trip.user.domain.UserVisitRepository;
import com.gabojago.trip.user.dto.UserVisitDto;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import net.bytebuddy.asm.Advice.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVisitServiceImpl implements UserVisitService {

    private final UserVisitRepository userVisitRepository;
    private final TicketService ticketService;

    @Autowired
    public UserVisitServiceImpl(UserVisitRepository userVisitRepository, TicketService ticketService) {
        this.userVisitRepository = userVisitRepository;
        this.ticketService = ticketService;
    }

    public List<UserVisitDto> getAllUserVisit(Integer userId) {
        List<UserVisit> userVisits = userVisitRepository.findAllByUserId(userId);

        return userVisits.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void addUserVisit(Integer userId) {
        LocalDate today = LocalDate.now();
        UserVisit visit = userVisitRepository.findVisitTodayByUserId(userId, today);
        if (visit == null) {
            visit = new UserVisit();
            visit.setUser(new User(userId));
            visit.setLastVisit(today);
            userVisitRepository.save(visit);

            // 티켓 발급
            ticketService.addTicket(userId);
        }
    }

    private UserVisitDto convertToDto(UserVisit userVisit) {
        UserVisitDto userVisitDto = new UserVisitDto();
        userVisitDto.setLastVisit(LocalDate.from(userVisit.getLastVisit()));
        userVisitDto.setUserId(userVisit.getUser().getId());
        return userVisitDto;
    }
}
