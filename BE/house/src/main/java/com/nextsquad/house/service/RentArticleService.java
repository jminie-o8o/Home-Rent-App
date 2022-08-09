package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.*;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
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
    private final RentArticleBookmarkRepository rentArticleBookmarkRepository;

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
        List<RentArticle> rentArticles = rentArticleRepository.findAllAvailable();
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .collect(Collectors.toList());

        return new RentArticleListResponse(responseElements);
    }
    
    public RentArticleResponse getRentArticle(Long id){
        RentArticle rentArticle = rentArticleRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (rentArticle.isDeleted() || rentArticle.isCompleted()) {
            throw new RuntimeException("삭제되었거나 거래가 완료된 글입니다.");
        }
        rentArticle.addViewCount();

        return new RentArticleResponse(rentArticle);
    }

    public GeneralResponseDto toggleIsCompleted(Long id) {
        RentArticle rentArticle = rentArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        rentArticle.toggleIsCompleted();
        return new GeneralResponseDto(200, "게시글 상태가 변경되었습니다.");
    }

    public GeneralResponseDto deleteArticle(Long id) {
        RentArticle rentArticle = rentArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        rentArticle.markAsDeleted();
        return new GeneralResponseDto(200, "게시글이 삭제되었습니다.");
    }

    public GeneralResponseDto addBookmark(BookmarkRequestDto bookmarkRequestDto) {
        User user = userRepository.findById(bookmarkRequestDto.getUserId()).orElseThrow(() -> new RuntimeException());
        RentArticle rentArticle = rentArticleRepository.findById(bookmarkRequestDto.getArticleId()).orElseThrow(() -> new RuntimeException());
        rentArticleBookmarkRepository.save(new RentArticleBookmark(rentArticle, user));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        User user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 유저가 없습니다."));
        RentArticle rentArticle = rentArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 양도 게시글이 없습니다."));
        RentArticleBookmark bookmark = rentArticleBookmarkRepository.findByUserAndRentArticle(user, rentArticle);
        rentArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }
}
