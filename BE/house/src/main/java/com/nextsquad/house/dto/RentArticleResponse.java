package com.nextsquad.house.dto;

import com.nextsquad.house.domain.house.HouseImage;
import com.nextsquad.house.domain.house.RentArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class RentArticleResponse {
    private Long id;
    private UserInfoDto user;
    private String address;
    private String addressDetail;
    private String addressDescription;
    private double latitude;
    private double longitude;
    private String title;
    private String content;
    private String contractType;
    private String houseType;
    private int deposit;
    private int rentFee;
    private int maintenanceFee;
    private String maintenanceFeeDescription;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private int bookmarkCount;
    private int maxFloor;
    private int thisFloor;
    private HouseFacilityListDto houseFacility;
    private List<String> houseImages;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime modifiedAt;
    private boolean isCompleted;
    private boolean isBookmarked;


    @Builder
    public RentArticleResponse(RentArticle rentArticle, boolean isBookmarked) {
        this.id = rentArticle.getId();
        this.user = UserInfoDto.from(rentArticle.getUser());
        this.address = rentArticle.getAddress();
        this.addressDetail = rentArticle.getAddressDetail();
        this.addressDescription = rentArticle.getAddressDescription();
        this.latitude = rentArticle.getLatitude();
        this.longitude = rentArticle.getLongitude();
        this.title = rentArticle.getTitle();
        this.content = rentArticle.getContent();
        this.contractType = String.valueOf(rentArticle.getContractType());
        this.houseType = String.valueOf(rentArticle.getHouseType());
        this.deposit = rentArticle.getDeposit();
        this.rentFee = rentArticle.getRentFee();
        this.maintenanceFee = rentArticle.getMaintenanceFee();
        this.maintenanceFeeDescription = rentArticle.getMaintenanceFeeDescription();
        this.availableFrom = rentArticle.getAvailableFrom();
        this.contractExpiresAt = rentArticle.getContractExpiresAt();
        this.bookmarkCount = rentArticle.getBookmarks().size();
        this.maxFloor = rentArticle.getMaxFloor();
        this.thisFloor = rentArticle.getThisFloor();
        this.houseFacility = HouseFacilityListDto.from(rentArticle.getHouseFacility());
        this.houseImages = convertHouseImageList(rentArticle.getHouseImages());
        this.createdAt = rentArticle.getCreatedAt();
        this.modifiedAt = rentArticle.getModifiedAt();
        this.isCompleted = rentArticle.isCompleted();
        this.isBookmarked = isBookmarked;
    }

    private static List<String> convertHouseImageList(List<HouseImage> houseImageList) {
        List<String> list = new ArrayList<>();
        for (HouseImage houseImage : houseImageList) {
            list.add(houseImage.getImageUrl());
        }
        return list;
    }
}
