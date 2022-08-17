package com.nextsquad.house.repository;

import com.nextsquad.house.domain.house.RentArticle;
import com.nextsquad.house.dto.SearchConditionDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.nextsquad.house.domain.house.QRentArticle.rentArticle;

@Slf4j
@RequiredArgsConstructor
public class CustomRentArticleRepositoryImpl implements CustomRentArticleRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RentArticle> findByKeyword(SearchConditionDto searchCondition, Pageable pageable) {
        log.info("keyword: {}, isCompleted: {}, sortedBy: {}", searchCondition.getKeyword(), searchCondition.getIsCompleted(), searchCondition.getSortedBy());
        List<RentArticle> content = jpaQueryFactory
                .select(rentArticle)
                .from(rentArticle)
                .where(rentArticle.isDeleted.eq(false),
                        checkAddressAndTitle(searchCondition.getKeyword()),
                        checkIsCompleted(searchCondition.getIsCompleted()))

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(checkSortCondition(searchCondition.getSortedBy()))
                .fetch();

        return content;
    }

    private BooleanExpression checkAddressAndTitle(String keyword) {
        if (keyword == null) {
            return null;
        }
        return (checkAddress(keyword)).or(checkTitle(keyword));
    }

    private BooleanExpression checkAddress(String keyword) {
        if (keyword == null) {
            return Expressions.asBoolean(false);
        }
        return rentArticle.address.like("%" + keyword + "%");
    }

    private BooleanExpression checkTitle(String keyword) {
        if (keyword == null) {
            return null;
        }
        return rentArticle.title.like("%" + keyword + "%");
    }

    private BooleanExpression checkIsCompleted(Boolean isCompleted) {
        if (isCompleted == null || !isCompleted) {
            return rentArticle.isCompleted.eq(false);
        }
        return rentArticle.isCompleted.eq(true);
    }

    private OrderSpecifier checkSortCondition(String sortedBy) {
        if (sortedBy == null) {
            return rentArticle.createdAt.desc();
        } else if (sortedBy.equals("rentFee")) {
            return rentArticle.rentFee.asc();
        } else if (sortedBy.equals("deposit")) {
            return rentArticle.deposit.asc();
        }
        return rentArticle.createdAt.desc();
    }
}
