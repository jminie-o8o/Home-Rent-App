package com.nextsquad.house.domain.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(fluent = true)
@Getter
public class HouseFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public void updateHouseFacility(HouseFacility facility) {
        this.hasAircon = facility.hasAircon();
        this.hasBed = facility.hasBed();
        this.hasCctv = facility.hasCctv();
        this.hasElevator = facility.hasElevator();
        this.hasLaundry = facility.hasLaundry();
        this.hasLobby = facility.hasLobby();
        this.hasParkingLot = facility.hasParkingLot();
        this.hasFridge = facility.hasFridge();
        this.hasTv = facility.hasTv();
        this.hasBalcony = facility.hasBalcony();
        this.hasVideoPhone = facility.hasVideoPhone();
    }
}

