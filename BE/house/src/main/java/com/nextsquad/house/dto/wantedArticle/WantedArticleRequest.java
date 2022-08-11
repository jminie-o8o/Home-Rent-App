package com.nextsquad.house.dto.wantedArticle;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WantedArticleRequest {

    private Long userId;
    private String address;
    private String title;
    private String content;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private int rentBudget;
    private int depositBudget;


}
