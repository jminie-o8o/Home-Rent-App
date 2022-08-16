package com.nextsquad.house.controller;

import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.service.RentArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses/rent")
public class RentArticleController {

    private final RentArticleService rentArticleService;

    @PostMapping
    public ResponseEntity<RentArticleCreationResponse> writeRentArticle(@RequestBody RentArticleRequest rentArticleRequest){
        return ResponseEntity.ok(rentArticleService.writeRentArticle(rentArticleRequest));
    }

    @GetMapping
    public ResponseEntity<RentArticleListResponse> getRentArticles(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(rentArticleService.getRentArticles(keyword, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentArticleResponse> getRentArticle(@PathVariable Long id) {
        return ResponseEntity.ok(rentArticleService.getRentArticle(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponseDto> modifyRentArticle(@PathVariable Long id, @RequestBody RentArticleRequest request) {
        return ResponseEntity.ok(rentArticleService.modifyRentArticle(id, request));
    }

    @PatchMapping("/{id}/isCompleted")
    public ResponseEntity<GeneralResponseDto> toggleIsCompleted(@PathVariable Long id) {
        return ResponseEntity.ok(rentArticleService.toggleIsCompleted(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseDto> deleteArticle(@PathVariable Long id) {
        return ResponseEntity.ok(rentArticleService.deleteArticle(id));
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

