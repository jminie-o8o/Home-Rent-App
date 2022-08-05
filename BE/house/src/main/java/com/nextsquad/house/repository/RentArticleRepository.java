package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentArticleRepository extends JpaRepository<RentArticle, Long> {
}
