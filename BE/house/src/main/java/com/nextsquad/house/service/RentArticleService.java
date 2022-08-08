package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.*;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.RentArticleCreationRequest;
import com.nextsquad.house.dto.RentArticleCreationResponse;
import com.nextsquad.house.dto.RentArticleListElement;
import com.nextsquad.house.dto.RentArticleListResponse;
import com.nextsquad.house.dto.RentArticleResponse;
import com.nextsquad.house.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RentArticleService {

    private final RentArticleRepository rentArticleRepository;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final FacilityInHomeRepository facilityInHomeRepository;
    private final HouseImageRepository houseImageRepository;
    private final SecurityRepository securityRepository;
    private final SecurityInHomeRepository securityInHomeRepository;

    public RentArticleCreationResponse writeRentArticle(RentArticleCreationRequest request){
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        List<String> houseImageUrls = request.getHouseImages();

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
                .hasElevator(request.isHasElevator())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        rentArticleRepository.save(rentArticle);

        for (int i = 0; i < houseImageUrls.size(); i++) {
            houseImageRepository.save(new HouseImage(houseImageUrls.get(i), rentArticle, i));
        }

        for (String facilityName : request.getFacilities()) {
            Facility facility = facilityRepository.findByName(facilityName)
                    .orElseThrow(() -> new RuntimeException());
            facilityInHomeRepository.save(new RentArticleFacility(rentArticle, facility));
        }

        for (String securityFacilityName : request.getSecurityFacilities()) {
            SecurityFacility securityFacility = securityRepository.findByName(securityFacilityName)
                    .orElseThrow(() -> new RuntimeException());
            securityInHomeRepository.save(new RentArticleSecurityFacility(rentArticle, securityFacility));
        }

        return new RentArticleCreationResponse(rentArticle.getId());
    }

    public RentArticleListResponse getRentArticles(String keyword, String sortedBy) {
        List<RentArticle> rentArticles = rentArticleRepository.findAll();
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .collect(Collectors.toList());

        return new RentArticleListResponse(responseElements);
    }
    
    public RentArticleResponse getRentArticle(Long id){
        RentArticle rentArticle = rentArticleRepository.findById(id).orElseThrow(() -> new RuntimeException());
        return new RentArticleResponse(rentArticle);
    }
}
