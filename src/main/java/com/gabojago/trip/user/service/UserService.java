package com.gabojago.trip.user.service;


import com.gabojago.trip.auth.oauth.OAuthUserInfo;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.dto.UserDto;
import com.gabojago.trip.user.dto.UserResDto;

import java.util.List;

public interface UserService {

  void addUser(UserDto userReqDto);

  User getUser(int userId);

  int modifyUser(int userId, UserDto userReqDto);

  List<UserDto> getAllUser();

  void delete(int userId);

    User getUserByProviderId(String provider, String providerId);

  User addOAuthUser(OAuthUserInfo oauthUser);
}
