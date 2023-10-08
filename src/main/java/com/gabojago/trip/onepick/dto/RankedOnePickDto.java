package com.gabojago.trip.onepick.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankedOnePickDto {

    private Integer userId;
    private String userProfileImage;
    private Integer likedCount;
    private OnePickDto onePickDto;

    @Builder
    public RankedOnePickDto(Integer userId, String userProfileImage, Integer likedCount, OnePickDto onePickDto) {
        this.userId = userId;
        this.userProfileImage = userProfileImage;
        this.likedCount = likedCount;
        this.onePickDto = onePickDto;
    }
}
