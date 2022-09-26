package com.nextsquad.house.dto;

import com.nextsquad.house.domain.house.HouseFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RentArticleRequest {

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
    private int deposit;
    private int rentFee;
    private int maintenanceFee;
    private String maintenanceFeeDescription;
    private LocalDate availableFrom;
    private LocalDate contractExpiresAt;
    private int maxFloor;
    private int thisFloor;
    private List<String> houseImages;
    private HouseFacilityListDto facility;


    public HouseFacility extractHouseFacility() {
        return HouseFacility.builder()
                .hasAircon(facility.isHasAircon())
                .hasBed(facility.isHasBed())
                .hasCctv(facility.isHasCctv())
                .hasElevator(facility.isHasElevator())
                .hasLaundry(facility.isHasLaundry())
                .hasLobby(facility.isHasLobby())
                .hasParkingLot(facility.isHasParkingLot())
                .hasRefriedge(facility.isHasRefriedge())
                .hasTv(facility.isHasTv())
                .hasValcony(facility.isHasValcony())
                .hasVideoPhone(facility.isHasVideoPhone())
                .build();
    }
}
