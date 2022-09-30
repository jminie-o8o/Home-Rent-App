package com.nextsquad.house.repository.wantedarticle;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.dto.SearchConditionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomWantedArticleRepository {
    List<WantedArticle> findByKeyword(SearchConditionDto searchCondition, Pageable pageable);
}
