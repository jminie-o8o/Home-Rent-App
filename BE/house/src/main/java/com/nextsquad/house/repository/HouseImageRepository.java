package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.HouseImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseImageRepository extends JpaRepository <HouseImage, Long> {
    List<HouseImage> findAllByImageUrlIn(List<String> imageUrls);
}
