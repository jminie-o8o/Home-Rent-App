package com.nextsquad.house.controller;

import com.nextsquad.house.dto.wantedArticle.SavedWantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import com.nextsquad.house.service.WantedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses/wanted")
public class WantedArticleController {

    private final WantedArticleService wantedArticleService;

    @PostMapping
    public ResponseEntity<SavedWantedArticleResponse> writeWantedArticle(@RequestBody WantedArticleRequest request) {
        return ResponseEntity.ok(wantedArticleService.writeWantedArticle(request));
    }

    @GetMapping
    public ResponseEntity<WantedArticleListResponse> getWantedArticleList() {
        return ResponseEntity.ok(wantedArticleService.getWantedArticleList());
    }
}
