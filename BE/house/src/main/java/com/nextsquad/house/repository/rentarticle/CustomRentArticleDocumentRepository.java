package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticleDocument;
import com.nextsquad.house.dto.SearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRentArticleDocumentRepository {
    List<RentArticleDocument> findByTitle(SearchCondition condition, Pageable pageable);
}