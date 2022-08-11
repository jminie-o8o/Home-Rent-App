package com.nextsquad.house.dto;

import com.nextsquad.house.domain.house.RentArticleFacility;
import com.nextsquad.house.domain.house.RentArticleSecurityFacility;

import java.time.LocalDate;
import java.util.List;

public class RentUpdateRequest {
    private Long userId;
    private String address;
    private String addressDetail;
    private String addressDescription;
    private double latitude;
    private double longitude;
    private String title;
    private String content;
    private String contractType;
    private String houseType;
    private List<RentArticleFacility> facilities;
    private List<RentArticleSecurityFacility> securityFacilities;
    private int deposit;
    private int rentFee;
    private int maintenanceFee;
    private String maintenanceFeeDescription;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private int maxFloor;
    private int thisFloor;
    private boolean hasParkingLot;
    private boolean hasBalcony;
    private boolean hasElevator;
    private List<String> houseImages;
}
