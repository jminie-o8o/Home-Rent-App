package com.nextsquad.house.dto.rentarticle;

import com.nextsquad.house.domain.house.RentArticle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RentArticleListResponse {
    private List<RentArticleListElement> rentArticles;
    private boolean hasNext;

    public static RentArticleListResponse of(List<RentArticle> rentArticles, Map<Long, Boolean> bookmarkHashMap, boolean hasNext) {
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .peek(element -> {element.setBookmarked(bookmarkHashMap.get(element.getId()) != null);})
                .collect(Collectors.toList());

        return new RentArticleListResponse(responseElements, hasNext);
    }

    public static RentArticleListResponse of(Page<RentArticle> rentArticles) {
        List<RentArticleListElement> responseElements = rentArticles.stream()
                .map(RentArticleListElement::from)
                .collect(Collectors.toList());
        return new RentArticleListResponse(responseElements, rentArticles.hasNext());
    }
}
