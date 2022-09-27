package com.nextsquad.house.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nextsquad.house.domain.house.*;
import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.*;
import com.nextsquad.house.dto.bookmark.BookmarkRequestDto;
import com.nextsquad.house.exception.*;
import com.nextsquad.house.login.jwt.JwtProvider;
import com.nextsquad.house.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final JwtProvider jwtProvider;

    public RentArticleCreationResponse writeRentArticle(RentArticleRequest request){
        log.info("writing {}... ", request.getTitle());
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException());
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

    public RentArticleListResponse getRentArticles(SearchConditionDto searchCondition, Pageable pageable, String token) {

        //유저로 찜리스트 조회
        DecodedJWT decode = jwtProvider.decode(token);
        Long id = decode.getClaim("id").asLong();
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        List<RentArticleBookmark> listByUser = rentArticleBookmarkRepository.findListByUser(user);
        Map<Long, Boolean> bookmarkHashMap = new HashMap<Long, Boolean>();
        for (RentArticleBookmark rentArticleBookmark : listByUser) {
            bookmarkHashMap.put(rentArticleBookmark.getRentArticle().getId(), true);
        }

        List<RentArticle> rentArticles = rentArticleRepository.findByKeyword(searchCondition, pageable);
        boolean hasNext = hasNext(pageable, rentArticles);
        if (hasNext) {
            rentArticles = rentArticles.subList(0, rentArticles.size()-1);
        }
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .peek(element -> {element.setBookmarked(bookmarkHashMap.get(element.getId()) != null);})
                .collect(Collectors.toList());

        return new RentArticleListResponse(responseElements, hasNext);
    }

    private boolean hasNext(Pageable pageable, List<RentArticle> rentArticles) {
        return pageable.getPageSize() < rentArticles.size();
    }

    public RentArticleResponse getRentArticle(Long id, String token){
        RentArticle rentArticle = rentArticleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException());
        if (rentArticle.isDeleted() || rentArticle.isCompleted()) {
            throw new IllegalArgumentException("삭제되었거나 거래가 완료된 글입니다.");
        }
        rentArticle.addViewCount();

        Long userId = jwtProvider.decode(token).getClaim("id").asLong();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        boolean isBookmarked = rentArticleBookmarkRepository.findByUserAndRentArticle(user, rentArticle).isPresent();

        return new RentArticleResponse(rentArticle, isBookmarked);
    }

    public GeneralResponseDto toggleIsCompleted(Long id, String accessToken) {
        RentArticle rentArticle = rentArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());

        authorizeArticleOwner(accessToken, rentArticle);

        rentArticle.toggleIsCompleted();
        return new GeneralResponseDto(200, "게시글 상태가 변경되었습니다.");
    }

    public GeneralResponseDto deleteArticle(Long id, String accessToken) {
        RentArticle rentArticle = rentArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());

        authorizeArticleOwner(accessToken, rentArticle);

        rentArticle.markAsDeleted();
        rentArticleBookmarkRepository.deleteByRentArticle(rentArticle);
        return new GeneralResponseDto(200, "게시글이 삭제되었습니다.");
    }

    public GeneralResponseDto addBookmark(BookmarkRequestDto bookmarkRequestDto, String token) {
        Long loginedId = jwtProvider.decode(token).getClaim("id").asLong();

        User user = userRepository.findById(loginedId).orElseThrow(() -> new UserNotFoundException());
        RentArticle rentArticle = rentArticleRepository.findById(bookmarkRequestDto.getArticleId()).orElseThrow(() -> new ArticleNotFoundException());

        if (rentArticleBookmarkRepository.findByUserAndRentArticle(user, rentArticle).isPresent()) {
            throw new DuplicateBookmarkException();
        }

        if (rentArticle.isDeleted()) {
            throw new IllegalArgumentException("삭제된 게시글은 추가할 수 없습니다.");
        }
        if (rentArticle.isCompleted()) {
            throw new IllegalArgumentException("삭제된 게시글은 추가할 수 없습니다.");
        }
        rentArticleBookmarkRepository.save(new RentArticleBookmark(rentArticle, user));
        return new GeneralResponseDto(200, "북마크에 추가 되었습니다.");
    }

    public GeneralResponseDto deleteBookmark(BookmarkRequestDto bookmarkRequestDto, String token) {
        Long loginedId = jwtProvider.decode(token).getClaim("id").asLong();

        User user = userRepository.findById(loginedId)
                .orElseThrow(() -> new UserNotFoundException());
        RentArticle rentArticle = rentArticleRepository.findById(bookmarkRequestDto.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException());
        RentArticleBookmark bookmark = rentArticleBookmarkRepository.findByUserAndRentArticle(user, rentArticle)
                .orElseThrow(() -> new BookmarkNotFoundException());
        rentArticleBookmarkRepository.delete(bookmark);
        return new GeneralResponseDto(200, "북마크가 삭제되었습니다.");
    }

    public GeneralResponseDto modifyRentArticle(Long id, RentArticleRequest request, String accessToken) {
        log.info("updating {}... ", request.getTitle());
        RentArticle rentArticle = rentArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException());

        authorizeArticleOwner(accessToken, rentArticle);

        facilityInHomeRepository.deleteAllByRentArticle(rentArticle);
        securityInHomeRepository.deleteAllByRentArticle(rentArticle);
        houseImageRepository.deleteAllByArticle(rentArticle);

        saveFacilityInHome(request.getFacilities(), rentArticle);
        saveSecurityInHome(request.getSecurityFacilities(), rentArticle);
        saveHouseImage(rentArticle, request.getHouseImages());

        rentArticle.modifyArticle(request);

        return new GeneralResponseDto(200, "게시글이 수정되었습니다.");
    }

    private void saveHouseImage(RentArticle rentArticle, List<String> houseImageUrls) {
        for (int i = 0; i < houseImageUrls.size(); i++) {
            houseImageRepository.save(new HouseImage(houseImageUrls.get(i), rentArticle, i));
        }
    }

    private void authorizeArticleOwner(String accessToken, RentArticle article) {
        Long loggedInId = jwtProvider.decode(accessToken).getClaim("id").asLong();
        User user = userRepository.findById(loggedInId)
                .orElseThrow(UserNotFoundException::new);

        if (!user.equals(article.getUser())) {
            throw new AccessDeniedException();
        }
    }
}
