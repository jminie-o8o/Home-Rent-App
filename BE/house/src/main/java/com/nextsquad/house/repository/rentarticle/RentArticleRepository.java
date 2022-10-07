package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentArticleRepository extends JpaRepository<RentArticle, Long>, CustomRentArticleRepository {
    @Query("select r from RentArticle r where r.isDeleted = false and r.isCompleted = false")
    Page<RentArticle> findAllAvailable(Pageable pageable);

    @Query("select r from RentArticle r where r.user = :user and r.isDeleted = false")
    Page<RentArticle> findByUser(@Param("user") User user, Pageable pageable);

    @Query("select r from RentArticle r join RentArticleBookmark b on r.id = b.rentArticle.id where b.user = :user")
    Page<RentArticle> findBookmarkedArticleByUser(@Param("user") User user, Pageable pageable);
}