package com.nextsquad.house.dto.bookmark;

import com.nextsquad.house.domain.house.ContractType;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class RentBookmarkElement {
    private Long id;
    private String title;
    private ContractType contractType;
    private int deposit;
    private int rentFee;
    private String mainImage;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private int bookmarkCount;

    //TODO: 북마크 count도 가져오기
    public static RentBookmarkElement from (RentArticleBookmark rentArticleBookmark) {
        return RentBookmarkElement.builder()
                .id(rentArticleBookmark.getId())
                .title(rentArticleBookmark.getRentArticle().getTitle())
                .contractType(rentArticleBookmark.getRentArticle().getContractType())
                .deposit(rentArticleBookmark.getRentArticle().getDeposit())
                .rentFee(rentArticleBookmark.getRentArticle().getRentFee())
                .mainImage(rentArticleBookmark.getRentArticle().getMainImage().getImageUrl())
                .availableFrom(rentArticleBookmark.getRentArticle().getAvailableFrom())
                .contractExpiresAt(rentArticleBookmark.getRentArticle().getContractExpiresAt())
                .build();
    }
}
