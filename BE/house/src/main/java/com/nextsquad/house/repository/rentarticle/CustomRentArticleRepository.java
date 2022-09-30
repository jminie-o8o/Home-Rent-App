package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.dto.SearchConditionDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRentArticleRepository {
    List<RentArticle> findByKeyword(SearchConditionDto searchCondition, Pageable pageable);
}
