package com.gabojago.trip.user.controller;


import com.gabojago.trip.user.dto.UserDto;
import com.gabojago.trip.user.dto.UserResDto;
import com.gabojago.trip.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Deprecated
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDto userDto) {   //FOR Test
        log.debug("[POST] /users " + userDto);
        userService.addUser(userDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    @Deprecated
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {  //FOR Test
        List<UserDto> userDtoList = userService.getAllUser();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResDto> get(@PathVariable int userId) {
        UserResDto userResDto = userService.getUser(userId);
        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> modify(@PathVariable int userId, @RequestBody UserDto userDto) {
        userService.modifyUser(userId,userDto);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable int userId) {
        userService.delete(userId);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
