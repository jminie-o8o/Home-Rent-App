package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.WantedArticle;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomWantedArticleRepository {
    List<WantedArticle> findByKeyword(String keyword, Pageable pageable);
}
