package com.nextsquad.house.dto;

import com.nextsquad.house.domain.house.HouseImage;
import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleFacility;
import com.nextsquad.house.domain.house.RentArticleSecurityFacility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
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
    private List<String> houseImages;
    private List<String> securityFacilities;


    public static RentArticleResponse from (RentArticle rentArticle) {
        return new RentArticleResponse(rentArticle.getId(), rentArticle.getAddress(),
                rentArticle.getAddressDetail(), rentArticle.getTitle(), rentArticle.getContent(),
                String.valueOf(rentArticle.getContractType()), String.valueOf(rentArticle.getHouseType()),
                RentArticleResponse.convertFacilityList(rentArticle.getFacilities()),
                rentArticle.getDeposit(), rentArticle.getRentFee(), rentArticle.getMaintenanceFee(),
                rentArticle.getAvailableFrom(), rentArticle.getContractExpiresAt(), rentArticle.getMaxFloor(),
                rentArticle.getThisFloor(), rentArticle.isHasParkingLot(), rentArticle.isHasParkingLot(),
                convertHouseImageList(rentArticle.getHouseImages()), convertSecurityFacilityList(rentArticle.getSecurityFacilities()));
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
