package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.nextsquad.house.domain.house.QRentArticle.rentArticle;

@RequiredArgsConstructor
public class CustomRentArticleRepositoryImpl implements CustomRentArticleRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public PageImpl<RentArticle> findbyKeyword(String keyword, Pageable pageable) {
        List<RentArticle> content = jpaQueryFactory
                .select(rentArticle)
                .from(rentArticle)
                .where(rentArticle.isCompleted.eq(false),
                        rentArticle.isDeleted.eq(false),
                        checkAddress(keyword),
                        checkTitle(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
//                .fetchResults();
                .fetch();

//        return new PageImpl<>(content.getResults(), pageable, content.getTotal());
        return new PageImpl<>(content, pageable, content.size());
    }

    private BooleanExpression checkAddress(String keyword) {
        if (keyword == null) {
            return null;
        }
        return rentArticle.address.like("%" + keyword + "%");
    }

    private BooleanExpression checkTitle(String keyword) {
        if (keyword == null) {
            return null;
        }
        return rentArticle.title.like("%" + keyword + "%");
    }
}
