package com.nextsquad.house.controller;

import com.nextsquad.house.dto.GeneralResponseDto;
import com.nextsquad.house.dto.SearchConditionDto;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.dto.wantedArticle.WantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.SavedWantedArticleResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleListResponse;
import com.nextsquad.house.dto.wantedArticle.WantedArticleRequest;
import com.nextsquad.house.service.WantedArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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


    @GetMapping("/{id}")
    public ResponseEntity<WantedArticleResponse> getWantedArticle(@PathVariable Long id) {
        return ResponseEntity.ok(wantedArticleService.getWantedArticle(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponseDto> updateWantedArticle(@PathVariable Long id, @RequestBody WantedArticleRequest request) {
        return ResponseEntity.ok(wantedArticleService.updateWantedArticle(id, request));
    }

    @GetMapping
    public ResponseEntity<WantedArticleListResponse> getWantedArticleList(@ModelAttribute SearchConditionDto searchConditionDto, Pageable pageable) {
        return ResponseEntity.ok(wantedArticleService.getWantedArticleList(searchConditionDto, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseDto> deleteWantedArticle(@PathVariable Long id){
        return ResponseEntity.ok(wantedArticleService.deleteWantedArticle(id));
    }

    @PostMapping("/bookmarks")
    public ResponseEntity<GeneralResponseDto> addWantedBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        return ResponseEntity.ok(wantedArticleService.addWantedBookmark(bookmarkRequestDto));
    }

    @DeleteMapping("/bookmarks")
    public ResponseEntity<GeneralResponseDto> deleteWantedBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        return ResponseEntity.ok(wantedArticleService.deleteWantedBookmark(bookmarkRequestDto));
    }

}
