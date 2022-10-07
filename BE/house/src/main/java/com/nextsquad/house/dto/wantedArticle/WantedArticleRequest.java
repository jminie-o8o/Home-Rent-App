package com.nextsquad.house.dto.wantedArticle;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WantedArticleRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String address;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private LocalDate moveInDate;

    @NotNull
    private LocalDate moveOutDate;

    private int rentBudget;
    private int depositBudget;


}
