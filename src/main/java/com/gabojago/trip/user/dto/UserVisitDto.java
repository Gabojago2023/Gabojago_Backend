package com.gabojago.trip.user.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserVisitDto {
    private LocalDate lastVisit;
    private Integer userId;
}
