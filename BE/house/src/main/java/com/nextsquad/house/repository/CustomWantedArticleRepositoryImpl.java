package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.dto.SearchConditionDto;
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
    public List<WantedArticle> findByKeyword(SearchConditionDto searchCondition, Pageable pageable) {

        List<WantedArticle> content = jpaQueryFactory
                .select(wantedArticle)
                .from(wantedArticle)
                .where(wantedArticle.isDeleted.eq(false),
                        checkAddressAndTitle(searchCondition.getKeyword()),
                        checkIsCompleted(searchCondition.getIsCompleted())
                )
                .orderBy(wantedArticle.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return content;
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

    private BooleanExpression checkIsCompleted(Boolean isCompleted) {
        if (isCompleted == null || !isCompleted) {
            return wantedArticle.isCompleted.eq(false);
        }
        return wantedArticle.isCompleted.eq(true);
    }
}
