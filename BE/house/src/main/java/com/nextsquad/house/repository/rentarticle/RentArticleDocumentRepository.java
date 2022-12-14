package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticleDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RentArticleDocumentRepository extends ElasticsearchRepository<RentArticleDocument, Long> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\" : \"?0\"}},{\"match\": {\"is_completed\": \"?1\"}},{\"match\": {\"is_deleted\": \"false\"}}]}}")
    List<RentArticleDocument> findByTitle(String title, boolean available, Pageable pageable);
}
