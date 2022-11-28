package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@Document(indexName = "rent_article")
public class RentArticleDocument {

    @Id
    private Long id;
    private User user;
    private String address;
    private String addressDetail;
    private String addressDescription;
    private double latitude;
    private double longitude;
    @Field(type = FieldType.Text)
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    private int deposit;
    @Field(name = "rent_fee")
    private int rentFee;
    private int maintenanceFee;
    private String maintenanceFeeDescription;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private ArticleStatus status;
    private int viewCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime modifiedAt;
    private HouseFacility houseFacility;
    private int maxFloor;
    private int thisFloor;
    private boolean isCompleted;
    private boolean isDeleted;

    @Builder.Default
    private List<HouseImage> houseImages = new ArrayList<>();

    @Builder.Default
    private List<RentArticleBookmark> bookmarks = new ArrayList<>();
}
