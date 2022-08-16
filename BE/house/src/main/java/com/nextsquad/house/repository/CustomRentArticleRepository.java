package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRentArticleRepository {
    List<RentArticle> findbyKeyword(String keyword, Pageable pageable);
}
