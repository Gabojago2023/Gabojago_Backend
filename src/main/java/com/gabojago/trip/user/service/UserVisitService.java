package com.gabojago.trip.user.service;

import com.gabojago.trip.user.dto.UserVisitDto;
import java.util.List;

public interface UserVisitService {
    List<UserVisitDto> getAllUserVisit(Integer userId);

}
