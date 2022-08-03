package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RentArticleCreationRequest {

    private Long userId;
    private String address;
    private String addressDetail;
    private String title;
    private String content;
    private String contractType;
    private String houseType;
    private int deposit;
    private int rentFee;
    private int maintenanceFee;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private int maxFloor;
    private int thisFloor;
    private boolean hasParkingLot;
    private boolean hasBalcony;
}
