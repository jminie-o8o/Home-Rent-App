package com.nextsquad.house.controller;

import com.nextsquad.house.dto.GeneralResponse;
import com.nextsquad.house.dto.SearchCondition;
import com.nextsquad.house.dto.bookmark.BookmarkRequest;
import com.nextsquad.house.dto.wantedArticle.WantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.SavedWantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import com.nextsquad.house.service.WantedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses/wanted")
public class WantedArticleController {

    private final WantedArticleService wantedArticleService;

    @PostMapping
    public ResponseEntity<SavedWantedArticleResponse> writeWantedArticle(@Valid @RequestBody WantedArticleRequest request, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(wantedArticleService.writeWantedArticle(request, token));
    }


    @GetMapping("/{id}")
    public ResponseEntity<WantedArticleResponse> getWantedArticle(@PathVariable Long id, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(wantedArticleService.getWantedArticle(id, token));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponse> updateWantedArticle(@PathVariable Long id, @RequestBody WantedArticleRequest request, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(wantedArticleService.updateWantedArticle(id, request, token));
    }

    @GetMapping
    public ResponseEntity<WantedArticleListResponse> getWantedArticleList(@ModelAttribute SearchCondition searchCondition, Pageable pageable,
                                                                          @RequestHeader(value="access-token") String token) {
        return ResponseEntity.ok(wantedArticleService.getWantedArticleList(searchCondition, pageable, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deleteWantedArticle(@PathVariable Long id, @RequestHeader(value = "access-token") String token){
        return ResponseEntity.ok(wantedArticleService.deleteWantedArticle(id, token));
    }

    @PostMapping("/bookmarks")
    public ResponseEntity<GeneralResponse> addWantedBookmark(@Valid @RequestBody BookmarkRequest request, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(wantedArticleService.addWantedBookmark(request, token));
    }

    @DeleteMapping("/bookmarks")
    public ResponseEntity<GeneralResponse> deleteWantedBookmark(@Valid @RequestBody BookmarkRequest request, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(wantedArticleService.deleteWantedBookmark(request, token));
    }

}
