package com.nextsquad.house.domain.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
