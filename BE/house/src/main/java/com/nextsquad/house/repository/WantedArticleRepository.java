package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.WantedArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WantedArticleRepository extends JpaRepository<WantedArticle, Long> {
    @Query("select a from WantedArticle a where a.isCompleted = false and a.isDeleted = false")
    List<WantedArticle> findByAvailable();
}
