package com.nextsquad.house.dto.wantedArticle;

import com.nextsquad.house.domain.house.WantedArticle;
import com.nextsquad.house.domain.house.WantedArticleBookmark;
import com.nextsquad.house.dto.rentarticle.RentArticleListElement;
import com.nextsquad.house.dto.rentarticle.RentArticleListResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class WantedArticleListResponse {
    private List<WantedArticleElementResponse> wantedArticles;
    private boolean hasNext;

    public WantedArticleListResponse(List<WantedArticleElementResponse> wantedArticles) {
        this.wantedArticles = wantedArticles;
    }

    public static WantedArticleListResponse of(List<WantedArticle> wantedArticles, Map<Long, Boolean> bookmarkHashMap, boolean hasNext) {
        List<WantedArticleElementResponse> elementResponseList = wantedArticles.stream()
                .map(WantedArticleElementResponse::from)
                .peek(element -> {element.setBookmarked(bookmarkHashMap.get(element.getId()) != null);})
                .collect(Collectors.toList());

        return new WantedArticleListResponse(elementResponseList, hasNext);
    }

    public static WantedArticleListResponse of(Page<WantedArticleBookmark> wantedArticles) {
        List<WantedArticleElementResponse> responseElements = wantedArticles.stream()
                .map(WantedArticleElementResponse::from)
                .collect(Collectors.toList());
        return new WantedArticleListResponse(responseElements, wantedArticles.hasNext());
    }
}
