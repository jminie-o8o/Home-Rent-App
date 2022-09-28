package com.nextsquad.house.domain.house;

import com.nextsquad.house.domain.user.User;
import com.nextsquad.house.dto.RentArticleRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentArticleTest {

    @Test
    @DisplayName("getImageUrls()를 호출하면 이미지 리스트가 반환되어야 한다.")
    public void getImageUrlsTest() {
        List<HouseImage> houseImages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HouseImage image = new HouseImage("testUrl" + i, null, i + 1);
            houseImages.add(image);
        }
        RentArticle article = RentArticle.builder()
                .houseImages(houseImages)
                .build();
        List<String> houseImageUrls = article.getHouseImageUrls();

        for (int i = 0; i < 10; i++) {
            assertThat(houseImageUrls.get(i)).isEqualTo(houseImages.get(i).getImageUrl());
        }
    }

    @Test
    @DisplayName("toggleIsCompleted()를  호출하면 isCompleted 필드가 현재 상태의 반대로 되어야 한다.")
    public void toggleIsCompletedTest() {
        RentArticle article = new RentArticle();
        assertThat(article.isCompleted()).isFalse();

        article.toggleIsCompleted();
        assertThat(article.isCompleted()).isTrue();

        article.toggleIsCompleted();
        assertThat(article.isCompleted()).isFalse();
    }

    @Test
    @DisplayName("markAsDeleted()를 호출하면 아티클의 상태가 삭제로 변경되어야 한다.")
    public void markAsDeletedTest() {
        RentArticle article = new RentArticle();
        assertThat(article.isDeleted()).isFalse();

        article.markAsDeleted();
        assertThat(article.isDeleted()).isTrue();

        article.markAsDeleted();
        assertThat(article.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("modifyArticle()을 호출하면 아티클의 내용이 변경된다.")
    public void modifyArticleTest() {
        User user = new User();
        final int VIEW_COUNT = 100;


        RentArticle article = RentArticle.builder()
                .user(user)
                .address("sample address")
                .addressDetail("sample detail")
                .addressDescription("sample discription")
                .latitude(10.0)
                .longitude(10.0)
                .title("sample title")
                .content("sample content")
                .contractType(ContractType.MONTHLY)
                .houseType(HouseType.ONEROOM)
                .deposit(10000)
                .rentFee(10000)
                .maintenanceFee(10000)
                .maintenanceFeeDescription("sample description")
                .availableFrom(LocalDate.now())
                .contractExpiresAt(LocalDate.now())
                .status(ArticleStatus.OPEN)
                .viewCount(VIEW_COUNT)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .maxFloor(5)
                .thisFloor(3)
                .bookmarks(new ArrayList<>())
                .isCompleted(false)
                .isDeleted(false)
                .build();

        article.modifyArticle(RentArticleRequest.builder()
                .contractType("JEONSE")
                .houseType("TWOROOM")
                .build());

        assertThat(article.getUser()).isEqualTo(user);
        assertThat(article.getAddress()).isNull();
        assertThat(article.getAddressDetail()).isNull();
        assertThat(article.getAddressDescription()).isNull();
        assertThat(article.getLatitude()).isEqualTo(0);
        assertThat(article.getLongitude()).isEqualTo(0);
        assertThat(article.getTitle()).isNull();
        assertThat(article.getContent()).isNull();
        assertThat(article.getContractType()).isEqualTo(ContractType.JEONSE);
        assertThat(article.getHouseType()).isEqualTo(HouseType.TWOROOM);
        assertThat(article.getDeposit()).isEqualTo(0);
        assertThat(article.getRentFee()).isEqualTo(0);
        assertThat(article.getMaintenanceFee()).isEqualTo(0);
        assertThat(article.getMaintenanceFeeDescription()).isNull();
        assertThat(article.getAvailableFrom()).isNull();
        assertThat(article.getContractExpiresAt()).isNull();
        assertThat(article.getMaxFloor()).isEqualTo(0);
        assertThat(article.getThisFloor()).isEqualTo(0);
//        assertThat(article.isHasParkingLot()).isEqualTo(false);
//        assertThat(article.isHasBalcony()).isEqualTo(false);
//        assertThat(article.isHasElevator()).isEqualTo(false);

        assertThat(article.getUser()).isEqualTo(user);
        assertThat(article.getViewCount()).isEqualTo(VIEW_COUNT);
        assertThat(article.getStatus()).isEqualTo(ArticleStatus.OPEN);
        assertThat(article.isCompleted()).isEqualTo(false);
        assertThat(article.isDeleted()).isEqualTo(false);

    }

}
