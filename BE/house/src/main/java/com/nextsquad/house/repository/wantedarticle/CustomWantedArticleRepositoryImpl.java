package com.nextsquad.house.repository.wantedarticle;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.dto.SearchCondition;
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
    public List<WantedArticle> findByKeyword(SearchCondition searchCondition, Pageable pageable) {

        return jpaQueryFactory
                .select(wantedArticle)
                .from(wantedArticle)
                .where(wantedArticle.isDeleted.eq(false),
                        checkAddressAndTitle(searchCondition.getKeyword()),
                        checkAvailableOnly(searchCondition.getAvailableOnly())
                )
                .orderBy(wantedArticle.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    private BooleanExpression checkAddressAndTitle(String keyword) {
        if (keyword == null) {
            return null;
        }
        return (checkAddress(keyword).or(checkTitle(keyword)));
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

    private BooleanExpression checkAvailableOnly(Boolean availableOnly) {
        if (availableOnly != null && availableOnly) {
            return wantedArticle.isCompleted.eq(false);
        }
        return null;
    }
}
