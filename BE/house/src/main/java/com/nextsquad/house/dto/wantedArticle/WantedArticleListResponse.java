package com.nextsquad.house.dto.wantedArticle;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WantedArticleListResponse {
    private List<WantedArticleElementResponse> wantedArticles;
}
