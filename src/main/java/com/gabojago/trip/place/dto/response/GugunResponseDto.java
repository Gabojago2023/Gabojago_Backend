package com.gabojago.trip.place.dto.response;

import com.gabojago.trip.place.domain.Gugun;
import lombok.Getter;

@Getter
public class GugunResponseDto {

    private Integer gugunCode;
    private String gugunName;

    public GugunResponseDto(Gugun gugun) {
        this.gugunCode = gugun.getGugunCode();
        this.gugunName = gugun.getGugunName();
    }

    public GugunResponseDto(Integer gugunCode, String gugunName) {
        this.gugunCode = gugunCode;
        this.gugunName = gugunName;
    }
}