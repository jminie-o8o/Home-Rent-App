package com.nextsquad.house.controller;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.dto.RentArticleCreationRequest;
import com.nextsquad.house.dto.RentArticleCreationResponse;
import com.nextsquad.house.service.RentArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
public class RentArticleController {

    private final RentArticleService rentArticleService;

    @PostMapping
    public ResponseEntity<RentArticleCreationResponse> writeRentArticle(@RequestBody RentArticleCreationRequest rentArticleCreationRequest){
        return ResponseEntity.ok(rentArticleService.writeRentArticle(rentArticleCreationRequest));
    }
}
