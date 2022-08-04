package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.*;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.RentArticleCreationRequest;
import com.nextsquad.house.dto.RentArticleCreationResponse;
import com.nextsquad.house.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentArticleService {

    private final RentArticleRepository rentArticleRepository;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final RentArticleFacilityRepository rentArticleFacilityRepository;
    private final HouseImageRepository houseImageRepository;

    public RentArticleCreationResponse writeRentArticle(RentArticleCreationRequest request){
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        List<HouseImage> houseImages = houseImageRepository.findAllByImageUrlIn(request.getHouseImages());
        RentArticle rentArticle = RentArticle.builder()
                .user(user)
                .title(request.getTitle())
                .houseType(HouseType.valueOf(request.getHouseType()))
                .rentFee(request.getRentFee())
                .deposit(request.getDeposit())
                .availableFrom(request.getAvailableFrom())
                .contractExpiresAt(request.getContractExpiresAt())
                .maintenanceFee(request.getMaintenanceFee())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .content(request.getContent())
                .contractType(ContractType.valueOf(request.getContractType()))
                .maxFloor(request.getMaxFloor())
                .thisFloor(request.getThisFloor())
                .hasParkingLot(request.isHasParkingLot())
                .hasBalcony(request.isHasBalcony())
                .houseImages(houseImages)
                .build();

        RentArticle savedArticle = rentArticleRepository.save(rentArticle);

        for (String facilityName : request.getFacilities()) {
            Facility facility = facilityRepository.findByName(facilityName)
                    .orElseThrow(() -> new RuntimeException());
            rentArticleFacilityRepository.save(new RentArticleFacility(rentArticle, facility));
        }

        return new RentArticleCreationResponse(savedArticle.getId());
    }
}
