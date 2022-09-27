package com.nextsquad.house.controller;

import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.service.RentArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/houses/rent")
public class RentArticleController {

    private final RentArticleService rentArticleService;

    @PostMapping
    public ResponseEntity<RentArticleCreationResponse> writeRentArticle(@RequestBody RentArticleRequest rentArticleRequest){
        return ResponseEntity.ok(rentArticleService.writeRentArticle(rentArticleRequest));
    }

    @GetMapping
    public ResponseEntity<RentArticleListResponse> getRentArticles(@ModelAttribute SearchConditionDto searchCondition, Pageable pageable,
                                                                   @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(rentArticleService.getRentArticles(searchCondition, pageable, token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentArticleResponse> getRentArticle(@PathVariable Long id, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(rentArticleService.getRentArticle(id, token));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponseDto> modifyRentArticle(@PathVariable Long id, @RequestBody RentArticleRequest request, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(rentArticleService.modifyRentArticle(id, request, token));
    }

    @PatchMapping("/{id}/isCompleted")
    public ResponseEntity<GeneralResponseDto> toggleIsCompleted(@PathVariable Long id, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(rentArticleService.toggleIsCompleted(id, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseDto> deleteArticle(@PathVariable Long id, @RequestHeader(value = "access-token") String token) {
        return ResponseEntity.ok(rentArticleService.deleteArticle(id, token));
    }

    @PostMapping("/bookmarks")
    public ResponseEntity<GeneralResponseDto> addBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto){
        return ResponseEntity.ok(rentArticleService.addBookmark(bookmarkRequestDto));
    }

    @DeleteMapping("/bookmarks")
    public ResponseEntity<GeneralResponseDto> deleteBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        return ResponseEntity.ok(rentArticleService.deleteBookmark(bookmarkRequestDto));
    }

}

