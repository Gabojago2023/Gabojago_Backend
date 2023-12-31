package com.gabojago.trip.user.controller;


import com.gabojago.trip.auth.exception.UserUnAuthorizedException;
import com.gabojago.trip.auth.service.AuthService;
import com.gabojago.trip.image.util.FileManageUtil;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.dto.MailDto;
import com.gabojago.trip.user.dto.NicknameDto;
import com.gabojago.trip.user.dto.RandomNicknameDto;
import com.gabojago.trip.user.dto.UserDto;
import com.gabojago.trip.user.dto.UserResDto;
import com.gabojago.trip.user.exception.NicknameAlreadyExistException;
import com.gabojago.trip.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private AuthService authService;
    private FileManageUtil fileManageUtil;


    public UserController(UserService userService, AuthService authService,
            FileManageUtil fileManageUtil) {
        this.userService = userService;
        this.authService = authService;
        this.fileManageUtil = fileManageUtil;
    }

    @Deprecated
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDto userDto) {   //FOR Test
        log.debug("[POST] /users " + userDto);
        userService.addUser(userDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }



    @GetMapping("/nickname-random")
    public ResponseEntity<RandomNicknameDto> randomNickname() {
        RandomNicknameDto randomNickname = userService.getRandomNickname();
        return new ResponseEntity<>(randomNickname, HttpStatus.OK);
    }

    @GetMapping("/nickname-available")
    public ResponseEntity<NicknameDto> isAvailable(@RequestHeader("Authorization") String token,
            @RequestParam String nickname) {
        Integer userId = authService.getUserIdFromToken(token);
        NicknameDto nicknameDto = userService.isNicknameAvailable(userId, nickname);
        return new ResponseEntity<>(nicknameDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResDto> get(@PathVariable int userId) {
        User logined = userService.getUser(userId);
        UserResDto userResDto = UserResDto.from(logined);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modify(@RequestHeader("Authorization") String token,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @RequestPart(value = "nickname", required = false) String nickname)
            throws NicknameAlreadyExistException {
        log.debug("[PUT] /user : file " + multipartFile);
        log.debug("[PUT] /user : nickname " + URLDecoder.decode(nickname, StandardCharsets.UTF_8));

        Integer id = authService.getUserIdFromToken(token);

        String filePath = null;
        if (multipartFile != null
                && !multipartFile.isEmpty()) {
            filePath = fileManageUtil.uploadFile(multipartFile);
        }

        log.debug("[filePath]>>>> " + filePath);

        userService.modifyUser(id, URLDecoder.decode(nickname, StandardCharsets.UTF_8), filePath);

        return new ResponseEntity<>("수정 성공", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable int userId) {
        userService.delete(userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PutMapping("/find-password")
    public ResponseEntity<?> sendEmail(@RequestBody UserDto userDto) {
        MailDto mailDto = userService.createMailAndChangePassword(userDto.getEmail());
        log.debug("보낸 메일 정보: " + mailDto);
        userService.mailSend(mailDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
