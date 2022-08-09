package com.nextsquad.house.dto.bookmark;

import com.nextsquad.house.domain.house.RentArticleBookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RentBookmarkListResponse {
    private List<RentBookmarkElement> rentBookmarkList;

    public static RentBookmarkListResponse from(List<RentArticleBookmark> bookmarks) {
        return new RentBookmarkListResponse(bookmarks.stream().map(RentBookmarkElement::from).collect(Collectors.toList()));
    }
}
