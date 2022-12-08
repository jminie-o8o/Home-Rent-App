package com.nextsquad.house.domain.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(indexName = "noritest_rent_article")
public class RentArticleDocument {

    @Id
    private Long id;
    @Field(name = "user_id")
    private Long userId;
    @Field(name = "address")
    private String address;
    @Field(name = "address_detail")
    private String addressDetail;
    @Field(name = "address_description")
    private String addressDescription;
    @Field(name = "latitude")
    private double latitude;
    @Field(name = "longitude")
    private double longitude;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(name = "content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Field(name = "contract_type")
    private ContractType contractType;
    @Enumerated(EnumType.STRING)
    @Field(name = "house_type")
    private HouseType houseType;
    @Field(name = "deposit")
    private int deposit;
    @Field(name = "rent_fee")
    private int rentFee;
    @Field(name = "maintenance_fee")
    private int maintenanceFee;
    @Field(name = "maintenance_fee_description")
    private String maintenanceFeeDescription;

    @Field(name = "available_from", type = FieldType.Date)
    private LocalDate availableFrom;
    @Field(name = "contract_expires_at", type = FieldType.Date)
    private LocalDate contractExpiresAt;

    @Field(name = "status")
    private ArticleStatus status;
    @Field(name = "view_count")
    private int viewCount;

    @Field(name = "created_at", type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    private LocalDateTime createdAt;

    @Field(name = "modified_at", type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
    private LocalDateTime modifiedAt;

    @Field(name = "house_facility")
    private HouseFacility houseFacility;
    @Field(name = "max_floor")
    private int maxFloor;
    @Field(name = "this_floor")
    private int thisFloor;
    @Field(name = "is_completed")
    private boolean isCompleted;
    @Field(name = "is_deleted")
    private boolean isDeleted;

    @Builder.Default
    private List<HouseImage> houseImages = new ArrayList<>();

    @Builder.Default
    private List<RentArticleBookmark> bookmarks = new ArrayList<>();
}
