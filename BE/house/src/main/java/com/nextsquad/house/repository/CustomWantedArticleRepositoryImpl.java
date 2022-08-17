package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.WantedArticle;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.nextsquad.house.domain.house.QWantedArticle.wantedArticle;

@Slf4j
@RequiredArgsConstructor
public class CustomWantedArticleRepositoryImpl implements CustomWantedArticleRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<WantedArticle> findByKeyword(String keyword, Pageable pageable) {
        log.info("keyword: {}", keyword);
        List<WantedArticle> content = jpaQueryFactory
                .select(wantedArticle)
                .from(wantedArticle)
                .where(wantedArticle.isCompleted.eq(false),
                        wantedArticle.isDeleted.eq(false),
                        checkAddress(keyword).or(checkTitle(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return content;
    }

    private BooleanExpression checkAddress(String keyword) {
        if (keyword == null) {
            return null;
        }
        return wantedArticle.address.like("%" + keyword + "%");
    }

    private BooleanExpression checkTitle(String keyword) {
        if (keyword == null) {
            return null;
        }
        return wantedArticle.title.like("%" + keyword + "%");
    }
}
