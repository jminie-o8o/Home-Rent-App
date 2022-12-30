package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RentArticleDocumentRepository extends ElasticsearchRepository<RentArticleDocument, Long>, CustomRentArticleDocumentRepository {


}
