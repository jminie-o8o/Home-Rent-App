package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FacilityInHomeRepository extends JpaRepository<RentArticleFacility, Long> {
    @Modifying
    @Query("delete from RentArticleFacility f where f.rentArticle = :rentArticle")
    int deleteAllByRentArticle(RentArticle rentArticle);
}
