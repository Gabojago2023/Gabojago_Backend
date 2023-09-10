package com.gabojago.trip.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    @Builder
    public JwtDto( String accessToken, String refreshToken,Integer userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }
}
