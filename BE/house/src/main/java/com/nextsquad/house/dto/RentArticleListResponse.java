package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class RentArticleListResponse {
    private List<RentArticleListElement> rentArticles;

    public RentArticleListResponse(List<RentArticleListElement> rentArticles) {
        this.rentArticles = rentArticles;
    }
}
