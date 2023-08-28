package com.gabojago.trip.place.dto.response;

import com.gabojago.trip.place.domain.Sido;
import lombok.Getter;

@Getter
public class SidoResponseDto {

    private final Integer id;
    private final String sidoName;
    private final String imageUrl;

    public SidoResponseDto(Sido sido) {
        this.id = sido.getSidoCode();
        this.sidoName = sido.getSidoName();
        this.imageUrl = sido.getImageUrl();
    }
}
