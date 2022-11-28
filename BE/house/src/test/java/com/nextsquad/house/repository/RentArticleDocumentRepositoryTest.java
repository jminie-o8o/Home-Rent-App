package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.RentArticleDocument;
import com.nextsquad.house.repository.rentarticle.RentArticleDocumentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
public class RentArticleDocumentRepositoryTest {

    @Autowired
    private RentArticleDocumentRepository rentArticleDocumentRepository;

//    @TestConfiguration
//    static class TestConfig {
//        @PersistenceContext
//        private EntityManager em;
//        @Bean
//        public JPAQueryFactory jpaQueryFactory(){
//            return new JPAQueryFactory(em);
//        }
//    }

    @Test
    @DisplayName("엘라스틱서치 테스트")
    void findBytitle() {

        List<RentArticleDocument> articles = rentArticleDocumentRepository.findByTitle("성동구", PageRequest.of(0, 5));
        for (int i = 0; i < articles.size(); i++) {
            System.out.println("title: " + articles.get(i).getTitle());
        }

    }

}
