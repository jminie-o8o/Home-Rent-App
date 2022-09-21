package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RentArticleListResponse {
    private List<RentArticleListElement> rentArticles;
    private boolean hasNext;

    public RentArticleListResponse(List<RentArticleListElement> rentArticles) {
        this.rentArticles = rentArticles;
    }
}
