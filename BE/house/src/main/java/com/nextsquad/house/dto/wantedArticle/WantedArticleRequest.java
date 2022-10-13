package com.nextsquad.house.dto.wantedArticle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class WantedArticleRequest {

    @NotBlank
    @Length(max = 255, message = "주소는 255자 이내로 작성해주세요")
    private String address;

    @NotBlank
    @Length(max = 255)
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private LocalDate moveInDate;

    @NotNull
    @Future
    private LocalDate moveOutDate;

    private int rentBudget;
    private int depositBudget;


}
