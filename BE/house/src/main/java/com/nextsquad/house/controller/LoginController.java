package com.nextsquad.house.controller;

import com.nextsquad.house.dto.JwtResponseDto;
import com.nextsquad.house.dto.OauthLoginRequestDto;
import com.nextsquad.house.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/oauth/callback")
    public String getOauthAuthCode() {
        return "URL 쿼리로 응답받은 auth 코드로 서버에 로그인 요청을 보내주세요.";
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<JwtResponseDto> loginWithOauth(@RequestBody OauthLoginRequestDto requestDto) {
        return ResponseEntity.ok(loginService.loginWithOauth(requestDto));
    }
}
