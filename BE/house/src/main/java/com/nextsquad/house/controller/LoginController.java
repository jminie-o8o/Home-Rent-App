package com.nextsquad.house.controller;

import com.nextsquad.house.dto.login.JwtResponse;
import com.nextsquad.house.dto.login.OauthLoginRequest;
import com.nextsquad.house.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/oauth/callback")
    public String getOauthAuthCode() {
        return "";
    }

    @PostMapping("/oauth")
    public ResponseEntity<JwtResponse> loginWithOauth(@Valid @RequestBody OauthLoginRequest requestDto) {
        return ResponseEntity.ok(loginService.loginWithOauth(requestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshJwtToken(@RequestHeader(value = "access-token") String accessToken, @RequestHeader(value = "refresh-token") String refreshToken) {
        return ResponseEntity.ok(loginService.refreshJwtToken(accessToken, refreshToken));
    }
}
