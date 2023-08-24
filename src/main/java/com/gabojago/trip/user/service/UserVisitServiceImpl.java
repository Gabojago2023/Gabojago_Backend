package com.gabojago.trip.user.service;

import com.gabojago.trip.user.domain.UserVisit;
import com.gabojago.trip.user.domain.UserVisitRepository;
import com.gabojago.trip.user.dto.UserVisitDto;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVisitServiceImpl implements UserVisitService {

    private final UserVisitRepository userVisitRepository;

    @Autowired
    public UserVisitServiceImpl(UserVisitRepository userVisitRepository) {
        this.userVisitRepository = userVisitRepository;
    }

    public List<UserVisitDto> getAllUserVisit(Integer userId) {
        List<UserVisit> userVisits = userVisitRepository.findAllByUserId(userId);

        return userVisits.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserVisitDto convertToDto(UserVisit userVisit) {
        UserVisitDto userVisitDto = new UserVisitDto();
        userVisitDto.setLastVisit(LocalDate.from(userVisit.getLastVisit()));
        userVisitDto.setUserId(userVisit.getUser().getId());
        return userVisitDto;
    }
}
