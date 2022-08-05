package com.nextsquad.house.controller;

import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.UserInfoDto;
import com.nextsquad.house.dto.UserResponseDto;
import com.nextsquad.house.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<GeneralResponseDto> modifyUserInfo(@PathVariable long userId, @RequestBody UserInfoDto userInfoDto) {
        return ResponseEntity.ok(userService.modifyUserInfo(userId, userInfoDto));
    }

}