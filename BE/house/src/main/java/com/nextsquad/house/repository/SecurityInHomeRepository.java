package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleSecurityFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityInHomeRepository extends JpaRepository<RentArticleSecurityFacility, Long> {
    @Modifying
    @Query("delete from RentArticleSecurityFacility f where f.rentArticle = :rentArticle")
    int deleteAllByRentArticle(RentArticle rentArticle);
}
