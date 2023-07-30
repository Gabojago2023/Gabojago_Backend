package com.gabojago.trip.auth.service;


import com.gabojago.trip.auth.domain.Auth;
import com.gabojago.trip.auth.dto.JwtDto;
import com.gabojago.trip.user.domain.User;

import java.util.Map;

public interface AuthService {

    JwtDto createTokens(User user);
    Map<String, Object> getClaimsFromToken(String token);

    Auth getSavedTokenByUserId(int id);

    String createAccessToken(Map<String, Object> claims);

    void saveTokens(Integer id, JwtDto jwtDto);

    void updateTokens(Auth saved);
}
