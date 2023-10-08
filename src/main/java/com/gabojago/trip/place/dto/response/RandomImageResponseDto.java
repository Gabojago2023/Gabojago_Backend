package com.gabojago.trip.place.dto.response;

import lombok.Getter;

@Getter
public class RandomImageResponseDto {

    private final String imgUrl;

    public RandomImageResponseDto(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
