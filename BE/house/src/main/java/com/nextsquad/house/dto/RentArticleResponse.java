package com.nextsquad.house.dto;

import com.nextsquad.house.domain.house.HouseImage;
import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleFacility;
import com.nextsquad.house.domain.house.RentArticleSecurityFacility;
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
    private Long userId;
    private String address;
    private String addressDetail;
    private String title;
    private String content;
    private String contractType;
    private String houseType;
    private List<String> facilities;
    private int deposit;
    private int rentFee;
    private int maintenanceFee;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private int maxFloor;
    private int thisFloor;
    private boolean hasParkingLot;
    private boolean hasBalcony;
    private boolean hasElevator;
    private List<String> houseImages;
    private List<String> securityFacilities;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd:HH:mm:ss")
    private LocalDateTime modifiedAt;
    private boolean isCompleted;


    @Builder
    public RentArticleResponse(RentArticle rentArticle) {
        this.userId = rentArticle.getId();
        this.address = rentArticle.getAddress();
        this.addressDetail = rentArticle.getAddressDetail();
        this.title = rentArticle.getTitle();
        this.content = rentArticle.getContent();
        this.contractType = String.valueOf(rentArticle.getContractType());
        this.houseType = String.valueOf(rentArticle.getHouseType());
        this.facilities = convertFacilityList(rentArticle.getFacilities());
        this.deposit = rentArticle.getDeposit();
        this.rentFee = rentArticle.getRentFee();
        this.maintenanceFee = rentArticle.getMaintenanceFee();
        this.availableFrom = rentArticle.getAvailableFrom();
        this.contractExpiresAt = rentArticle.getContractExpiresAt();
        this.maxFloor = rentArticle.getMaxFloor();
        this.thisFloor = rentArticle.getThisFloor();
        this.hasParkingLot = rentArticle.isHasParkingLot();
        this.hasBalcony = rentArticle.isHasBalcony();
        this.hasElevator = rentArticle.isHasElevator();
        this.houseImages = convertHouseImageList(rentArticle.getHouseImages());
        this.securityFacilities = convertSecurityFacilityList(rentArticle.getSecurityFacilities());
        this.createdAt = rentArticle.getCreatedAt();
        this.modifiedAt = rentArticle.getModifiedAt();
        this.isCompleted = rentArticle.isCompleted();
    }

    private static List<String> convertFacilityList(List<RentArticleFacility> facilityList){
        List<String> list = new ArrayList<>();
        for (RentArticleFacility rentArticleFacility : facilityList) {
            list.add(rentArticleFacility.getFacility().getName());
        }
        return list;
    }

    private static List<String> convertHouseImageList(List<HouseImage> houseImageList) {
        List<String> list = new ArrayList<>();
        for (HouseImage houseImage : houseImageList) {
            list.add(houseImage.getImageUrl());
        }
        return list;
    }

    private static List<String> convertSecurityFacilityList(List<RentArticleSecurityFacility> securityFacilityList) {
        List<String> list = new ArrayList<>();
        for (RentArticleSecurityFacility securityFacility : securityFacilityList) {
            list.add(securityFacility.getSecurityFacility().getName());
        }
        return list;
    }
}