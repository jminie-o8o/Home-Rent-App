package com.nextsquad.house.dto;

import com.nextsquad.house.domain.house.ContractType;
import com.nextsquad.house.domain.house.RentArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class RentArticleListElement {
    private Long id;
    private String title;
    private String houseImage;
    private ContractType contractType;
    private Integer deposit;
    private Integer rentFee;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private LocalDateTime createdAt;
    private boolean isCompleted;
    private boolean isDeleted;

    public static RentArticleListElement from(RentArticle article) {
        return RentArticleListElement.builder()
                .id(article.getId())
                .title(article.getTitle())
                .houseImage(article.getMainImage().getImageUrl())
                .contractType(article.getContractType())
                .deposit(article.getDeposit())
                .rentFee(article.getRentFee())
                .availableFrom(article.getAvailableFrom())
                .contractExpiresAt(article.getContractExpiresAt())
                .createdAt(article.getCreatedAt())
                .isCompleted(article.isCompleted())
                .isDeleted(article.isDeleted())
                .build();
    }

}
