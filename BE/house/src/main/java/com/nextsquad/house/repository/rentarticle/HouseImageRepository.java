package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.HouseImage;
import com.nextsquad.house.domain.house.RentArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseImageRepository extends JpaRepository <HouseImage, Long> {
    List<HouseImage> findAllByImageUrlIn(List<String> imageUrls);

    @Modifying
    @Query("delete from HouseImage i where i.rentArticle = :rentArticle")
    int deleteAllByArticle(@Param(value = "rentArticle") RentArticle rentArticle);
}
