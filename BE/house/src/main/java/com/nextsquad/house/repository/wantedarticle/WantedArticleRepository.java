package com.nextsquad.house.repository.wantedarticle;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WantedArticleRepository extends JpaRepository<WantedArticle, Long>, CustomWantedArticleRepository {

    @Query("select a from WantedArticle a where a.isDeleted = false and a.user = :user")
    Page<WantedArticle> findByUser(@Param("user") User user, Pageable pageable);

    Page<WantedArticle> findAll(Pageable pageable);

}
