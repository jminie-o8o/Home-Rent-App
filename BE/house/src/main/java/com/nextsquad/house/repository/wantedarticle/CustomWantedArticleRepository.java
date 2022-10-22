package com.nextsquad.house.repository.wantedarticle;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.dto.SearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomWantedArticleRepository {
    List<WantedArticle> findByKeyword(SearchCondition searchCondition, Pageable pageable);
}
