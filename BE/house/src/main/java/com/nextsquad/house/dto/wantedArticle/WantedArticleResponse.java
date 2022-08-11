package com.nextsquad.house.dto.wantedArticle;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class WantedArticleResponse {
    private Long id;
    private UserInfoDto user;
    private String address;
    private String title;
    private String content;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private int rentBudget;
    private int depositBudget;
    private int viewCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static WantedArticleResponse from(WantedArticle article) {
        UserInfoDto userInfoDto = UserInfoDto.from(article.getUser());
        return WantedArticleResponse.builder()
                .id(article.getId())
                .user(userInfoDto)
                .address(article.getAddress())
                .title(article.getTitle())
                .content(article.getContent())
                .moveInDate(article.getMoveInDate())
                .moveOutDate(article.getMoveOutDate())
                .rentBudget(article.getRentBudget())
                .depositBudget(article.getDepositBudget())
                .viewCount(article.getViewCount())
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .build();
    }

}
