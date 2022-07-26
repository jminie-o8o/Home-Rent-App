package com.nextsquad.house.dto.rentarticle;

import com.nextsquad.house.domain.house.HouseFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class HouseFacilityList {
    private boolean hasParkingLot;
    private boolean hasBalcony;
    private boolean hasElevator;
    private boolean hasAircon;
    private boolean hasLaundry;
    private boolean hasBed;
    private boolean hasFridge;
    private boolean hasTv;
    private boolean hasCctv;
    private boolean hasVideoPhone;
    private boolean hasLobby;

    public static HouseFacilityList from(HouseFacility houseFacility) {
        return HouseFacilityList.builder()
            .hasParkingLot(houseFacility.hasParkingLot())
            .hasBalcony(houseFacility.hasBalcony())
            .hasElevator(houseFacility.hasElevator())
            .hasAircon(houseFacility.hasAircon())
            .hasLaundry(houseFacility.hasLaundry())
            .hasBed(houseFacility.hasBed())
            .hasFridge(houseFacility.hasFridge())
            .hasTv(houseFacility.hasTv())
            .hasCctv(houseFacility.hasCctv())
            .hasVideoPhone(houseFacility.hasVideoPhone())
            .hasLobby(houseFacility.hasLobby())
            .build();
    }
}
