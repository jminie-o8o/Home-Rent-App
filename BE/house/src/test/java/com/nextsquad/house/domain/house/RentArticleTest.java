package com.nextsquad.house.domain.house;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}