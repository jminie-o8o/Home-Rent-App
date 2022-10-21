package com.nextsquad.house.controller;

import com.nextsquad.house.dto.GeneralResponse;
import com.nextsquad.house.dto.rentarticle.RentArticleListResponse;
import com.nextsquad.house.dto.user.UserInfo;
import com.nextsquad.house.dto.user.UserResponse;
import com.nextsquad.house.dto.user.DuplicationCheckResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<GeneralResponse> modifyUserInfo(@PathVariable long userId, @RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(userService.modifyUserInfo(userId, userInfo));
    }

    @GetMapping("{userId}/articles/rent")
    public ResponseEntity<RentArticleListResponse> getMyRentArticles(@PathVariable long userId, Pageable pageable) {
        return ResponseEntity.ok(userService.getMyRentArticles(userId, pageable));
    }

    @GetMapping("{userId}/bookmarks/rent")
    public ResponseEntity<RentArticleListResponse> getRentBookmark(@PathVariable long userId, Pageable pageable){
        return ResponseEntity.ok(userService.getRentBookmark(userId, pageable));
    }

    @GetMapping("/check-duplication")
    public ResponseEntity<DuplicationCheckResponse> checkDuplication(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.checkDuplication(nickname));
    }

    @GetMapping("/{userId}/bookmarks/wanted")
    public ResponseEntity<WantedArticleListResponse> getWantedBookmark(@PathVariable long userId, Pageable pageable) {
        return ResponseEntity.ok(userService.getWantedBookmark(userId, pageable));
    }

    @GetMapping("/{userId}/articles/wanted")
    public ResponseEntity<WantedArticleListResponse> getMyWantedArticles (@PathVariable long userId, Pageable pageable) {
        return ResponseEntity.ok(userService.getMyWantedArticles(userId, pageable));
    }
}
