package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.WantedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WantedArticleRepository extends JpaRepository<WantedArticle, Long> {
}
