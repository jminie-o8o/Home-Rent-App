package com.nextsquad.house.repository.rentarticle;

import com.nextsquad.house.domain.house.RentArticleDocument;
import com.nextsquad.house.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.StringQuery;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RentArticleDocumentRepositoryImpl implements CustomRentArticleDocumentRepository {

    private final ElasticsearchOperations operations;

    @Override
    public List<RentArticleDocument> findByTitle(SearchCondition condition, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("{\"bool\": {\"must\": [{\"match\": {\"title\" : \"")
                .append(condition.getKeyword())
                .append("\"}},{\"match\": {\"is_completed\": \"")
                .append(condition.getAvailableOnly())
                .append("\"}},{\"match\": {\"is_deleted\": \"false\"}}]}}")
                .append("\"sort\": [{\"created_at\" : \"desc\"}]}");

        SearchHits<RentArticleDocument> query = operations.search(new StringQuery(queryBuilder.toString()), RentArticleDocument.class);

        return query.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
