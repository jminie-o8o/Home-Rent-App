package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HouseFacilityListDto {
    private boolean hasParkingLot;
    private boolean hasValcony;
    private boolean hasElevator;
    private boolean hasAircon;
    private boolean hasLaundry;
    private boolean hasBed;
    private boolean hasRefriedge;
    private boolean hasTv;
    private boolean hasCctv;
    private boolean hasVideoPhone;
    private boolean hasLobby;
}
