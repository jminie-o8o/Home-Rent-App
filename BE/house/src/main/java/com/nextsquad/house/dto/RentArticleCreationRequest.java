package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class RentArticleCreationRequest {

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
    private List<String> facilities;
    private List<String> securityFacilities;
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
