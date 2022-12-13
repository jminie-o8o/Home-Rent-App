package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticleDocument;
import com.nextsquad.house.dto.SearchCondition;
import com.nextsquad.house.repository.rentarticle.RentArticleDocumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RentArticleDocumentRepositoryTest {

    @Autowired
    private RentArticleDocumentRepository rentArticleDocumentRepository;

    @Test
    @DisplayName("엘라스틱서치 테스트")
    void findByTitle() {
        SearchCondition condition = new SearchCondition(false, "createdAt", "왕십리");

        List<RentArticleDocument> articles = rentArticleDocumentRepository.findByTitle(condition.getKeyword(), condition.getAvailableOnly(), PageRequest.of(0, 10));
        assertThat(articles.size()).isEqualTo(9);
    }

}
