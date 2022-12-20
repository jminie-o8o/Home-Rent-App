package com.nextsquad.house.dto.rentarticle;

import com.nextsquad.house.domain.house.ContractType;
import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleBookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RentArticleListElement implements Serializable{
    private Long id;
    private String title;
    private String address;
    private List<String> houseImages;
    private ContractType contractType;
    private Integer deposit;
    private Integer rentFee;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private LocalDateTime createdAt;
    private boolean isCompleted;
    private boolean isDeleted;
    private boolean bookmarked;
    private int bookmarkCount;

    public static RentArticleListElement from(RentArticle article) {

        return RentArticleListElement.builder()
                .id(article.getId())
                .title(article.getTitle())
                .address(article.getAddress())
                .houseImages(article.getHouseImageUrls())
                .contractType(article.getContractType())
                .deposit(article.getDeposit())
                .rentFee(article.getRentFee())
                .availableFrom(article.getAvailableFrom())
                .contractExpiresAt(article.getContractExpiresAt())
                .createdAt(article.getCreatedAt())
                .isCompleted(article.isCompleted())
                .isDeleted(article.isDeleted())
                .bookmarkCount(article.getBookmarkCount())
                .build();
    }

    public static RentArticleListElement from (RentArticleBookmark rentArticleBookmark) {
        RentArticle rentArticle = rentArticleBookmark.getRentArticle();
        return RentArticleListElement.builder()
                .id(rentArticle.getId())
                .title(rentArticle.getTitle())
                .address(rentArticle.getAddress())
                .houseImages(rentArticle.getHouseImageUrls())
                .contractType(rentArticle.getContractType())
                .deposit(rentArticle.getDeposit())
                .rentFee(rentArticle.getRentFee())
                .availableFrom(rentArticle.getAvailableFrom())
                .contractExpiresAt(rentArticle.getContractExpiresAt())
                .createdAt(rentArticle.getCreatedAt())
                .isCompleted(rentArticle.isCompleted())
                .isDeleted(rentArticle.isDeleted())
                .bookmarked(true)
                .build();
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
