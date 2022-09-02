package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WantedArticleRepository extends JpaRepository<WantedArticle, Long>, CustomWantedArticleRepository {
    @Query("select a from WantedArticle a where a.isCompleted = false and a.isDeleted = false")
    Page<WantedArticle> findByAvailable(Pageable pageable);

    @Query("select a from WantedArticle a where a.isDeleted = false")
    Page<WantedArticle> findByUser(User user, Pageable pageable);

    Page<WantedArticle> findAll(Pageable pageable);

}
