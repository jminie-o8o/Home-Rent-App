package com.nextsquad.house.dto.rentarticle;

import com.nextsquad.house.domain.house.HouseFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class RentArticleRequest {

    private Long userId;

    @NotBlank
    @Length(max = 255, message = "주소는 255자 이내로 작성해주세요")
    private String address;

    @NotBlank
    @Length(max = 255)
    private String addressDetail;

    private String addressDescription;

    private double latitude;
    private double longitude;

    @NotBlank
    @Length(max = 255)
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private String contractType;

    @NotNull
    private String houseType;

    private int deposit;
    private int rentFee;
    private int maintenanceFee;

    @NotBlank
    private String maintenanceFeeDescription;

    @NotNull
    private LocalDate availableFrom;

    @NotNull
    @Future
    private LocalDate contractExpiresAt;
    private int maxFloor;
    private int thisFloor;
    private List<String> houseImages;
    @NotNull
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
                .hasFridge(facility.isHasFridge())
                .hasTv(facility.isHasTv())
                .hasBalcony(facility.isHasBalcony())
                .hasVideoPhone(facility.isHasVideoPhone())
                .build();
    }
}
