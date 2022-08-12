package com.nextsquad.house.dto.wantedArticle;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class WantedArticleElementResponse {

    private Long id;
    private String address;
    private String title;
    private String content;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private int rentBudget;
    private int depositBudget;
    private LocalDateTime createdAt;

    public static WantedArticleElementResponse from(WantedArticle article) {
        return WantedArticleElementResponse.builder()
                .id(article.getId())
                .address(article.getAddress())
                .title(article.getTitle())
                .content(article.getContent())
                .moveInDate(article.getMoveInDate())
                .moveOutDate(article.getMoveOutDate())
                .rentBudget(article.getRentBudget())
                .depositBudget(article.getDepositBudget())
                .createdAt(article.getCreatedAt())
                .build();
    }

    public static WantedArticleElementResponse from(WantedArticleBookmark wantedArticleBookmark) {
        WantedArticle article = wantedArticleBookmark.getWantedArticle();
        return WantedArticleElementResponse.builder()
                .id(article.getId())
                .address(article.getAddress())
                .title(article.getTitle())
                .content(article.getContent())
                .moveInDate(article.getMoveInDate())
                .moveOutDate(article.getMoveOutDate())
                .rentBudget(article.getRentBudget())
                .depositBudget(article.getDepositBudget())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
