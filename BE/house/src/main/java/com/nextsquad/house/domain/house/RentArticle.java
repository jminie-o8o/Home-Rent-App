package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.RentArticleCreationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "rent_article_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    private String address;
    private String addressDetail;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    private int deposit;
    private int rentFee;
    private int maintenanceFee;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private ArticleStatus status;
    private int viewCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime modifiedAt;
    private int maxFloor;
    private int thisFloor;
    private boolean hasParkingLot;
    private boolean hasBalcony;

}
