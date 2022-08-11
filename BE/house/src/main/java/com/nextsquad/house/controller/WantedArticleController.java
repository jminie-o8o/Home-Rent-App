package com.nextsquad.house.controller;

import com.nextsquad.house.dto.wantedArticle.SavedWantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import com.nextsquad.house.service.WantedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses/wanted")
public class WantedArticleController {

    private final WantedArticleService wantedArticleService;

    @PostMapping
    public ResponseEntity<SavedWantedArticleResponse> writeWantedArticle(@RequestBody WantedArticleRequest request) {
        return ResponseEntity.ok(wantedArticleService.writeWantedArticle(request));
    }
}
