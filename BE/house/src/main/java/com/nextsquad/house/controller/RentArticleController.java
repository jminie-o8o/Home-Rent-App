package com.nextsquad.house.controller;

import com.nextsquad.house.dto.RentArticleCreationRequest;
import com.nextsquad.house.dto.RentArticleCreationResponse;
import com.nextsquad.house.dto.RentArticleListResponse;
import com.nextsquad.house.dto.RentArticleResponse;
import com.nextsquad.house.service.RentArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
public class RentArticleController {

    private final RentArticleService rentArticleService;

    @PostMapping
    public ResponseEntity<RentArticleCreationResponse> writeRentArticle(@RequestBody RentArticleCreationRequest rentArticleCreationRequest){
        return ResponseEntity.ok(rentArticleService.writeRentArticle(rentArticleCreationRequest));
    }

    @GetMapping
    public ResponseEntity<RentArticleListResponse> getRentArticles(String keyword, String sortedBy) {
        return ResponseEntity.ok(rentArticleService.getRentArticles(keyword, sortedBy));

    @GetMapping("/{id}")
    public ResponseEntity<RentArticleResponse> getRentArticle(@PathVariable Long id) {
        return ResponseEntity.ok(rentArticleService.getRentArticle(id));

    }
}

