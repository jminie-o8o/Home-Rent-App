package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticleDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RentArticleDocumentRepository extends ElasticsearchRepository<RentArticleDocument, Long> {

    List<RentArticleDocument> findByTitle(String title, Pageable pageable);
}
