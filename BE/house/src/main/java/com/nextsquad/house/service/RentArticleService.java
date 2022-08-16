package com.nextsquad.house.service;

import com.nextsquad.house.domain.house.*;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public RentArticleCreationResponse writeRentArticle(RentArticleRequest request){
        log.info("writing {}... ", request.getTitle());
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
                .maintenanceFeeDescription(request.getMaintenanceFeeDescription())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .addressDescription(request.getAddressDescription())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
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

        saveHouseImage(rentArticle, houseImageUrls);
        saveFacilityInHome(request.getFacilities(), rentArticle);
        saveSecurityInHome(request.getSecurityFacilities(), rentArticle);

        return new RentArticleCreationResponse(rentArticle.getId());
    }

    private void saveSecurityInHome(List<String> securityFacilities, RentArticle rentArticle) {
        for (String securityFacilityName : securityFacilities) {
            SecurityFacility securityFacility = securityRepository.findByName(securityFacilityName)
                    .orElseThrow(() -> new RuntimeException());
            securityInHomeRepository.save(new RentArticleSecurityFacility(rentArticle, securityFacility));
        }
    }

    private void saveFacilityInHome(List<String> facilities, RentArticle rentArticle) {
        for (String facilityName : facilities) {
            Facility facility = facilityRepository.findByName(facilityName)
                    .orElseThrow(() -> new RuntimeException());
            facilityInHomeRepository.save(new RentArticleFacility(rentArticle, facility));
        }
    }

    public RentArticleListResponse getRentArticles(String keyword, Pageable pageable) {
//        Page<RentArticle> rentArticles = rentArticleRepository.findAllAvailable(pageable);
        Page<RentArticle> rentArticles = rentArticleRepository.findbyKeyword(keyword, pageable);
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .collect(Collectors.toList());

        return new RentArticleListResponse(responseElements,  hasNext(pageable, rentArticles));
    }

    private boolean hasNext(Pageable pageable, Page<RentArticle> rentArticles) {
        return pageable.getPageNumber() < rentArticles.getTotalPages() - 1;
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
        if (rentArticle.isDeleted()) {
            return new GeneralResponseDto(400, "삭제된 게시글은 추가할 수 없습니다.");
        }
        if (rentArticle.isCompleted()) {
            return new GeneralResponseDto(400, "완료된 게시글은 추가할 수 없습니다.");
        }
        rentArticleBookmarkRepository.save(new RentArticleBookmark(rentArticle, user));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto) {
        User user = userRepository.findById(bookmarkRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 유저가 없습니다."));
        RentArticle rentArticle = rentArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 양도 게시글이 없습니다."));
        RentArticleBookmark bookmark = rentArticleBookmarkRepository.findByUserAndRentArticle(user, rentArticle)
                .orElseThrow(() -> new RuntimeException("북마크가 존재하지 않습니다."));
        rentArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }

    public GeneralResponseDto modifyRentArticle(Long id, RentArticleRequest request) {
        log.info("updating {}... ", request.getTitle());
        RentArticle rentArticle = rentArticleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id에 맞는 양도 게시글이 없습니다."));

        facilityInHomeRepository.deleteAllByRentArticle(rentArticle);
        securityInHomeRepository.deleteAllByRentArticle(rentArticle);
        houseImageRepository.deleteAllByArticle(rentArticle);

        saveFacilityInHome(request.getFacilities(), rentArticle);
        saveSecurityInHome(request.getSecurityFacilities(), rentArticle);
        saveHouseImage(rentArticle, request.getHouseImages());

        return new GeneralResponseDto(200, "게시글이 수정되었습니다.");
    }

    private void saveHouseImage(RentArticle rentArticle, List<String> houseImageUrls) {
        for (int i = 0; i < houseImageUrls.size(); i++) {
            houseImageRepository.save(new HouseImage(houseImageUrls.get(i), rentArticle, i));
        }
    }
}
