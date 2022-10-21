package com.nextsquad.house.controller;

import com.nextsquad.house.dto.GeneralResponse;
import com.nextsquad.house.service.LogoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse> logout(@RequestHeader(value = "access-token") String accessToken,
                                                  @RequestHeader(value = "refresh-token") String refreshToken){
        log.info("access-token: {}, refresh-token: {}", accessToken, refreshToken);
        return ResponseEntity.ok(logoutService.logout(accessToken, refreshToken));
    }
}
