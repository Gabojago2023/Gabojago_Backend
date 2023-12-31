package com.gabojago.trip.user.service;


import com.gabojago.trip.auth.oauth.OAuthUserInfo;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.dto.MailDto;
import com.gabojago.trip.user.dto.NicknameDto;
import com.gabojago.trip.user.dto.RandomNicknameDto;
import com.gabojago.trip.user.dto.UserDto;

import java.util.List;

public interface UserService {

    void addUser(UserDto userReqDto);

    User getUser(int userId);

    void modifyUser(Integer id, String nickname, String imagePath);

    List<UserDto> getAllUser();

    void delete(int userId);

    User getUserByProviderId(String provider, String providerId);

    User addOAuthUser(OAuthUserInfo oauthUser);

    NicknameDto isNicknameAvailable(Integer userId, String nickname);

    RandomNicknameDto getRandomNickname();

    MailDto createMailAndChangePassword(String userEmail);

    void updatePassword(String str, String userEmail) throws Exception;

    String getTempPassword();

    void mailSend(MailDto mailDto);
}
