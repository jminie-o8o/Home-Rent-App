package com.nextsquad.house.dto;

import lombok.Getter;


@Getter
public class SearchCondition {

    private final Boolean availableOnly;
    private final String sortedBy;
    private final String keyword;
        public SearchCondition(Boolean availableOnly, String sortedBy, String keyword) {
        this.availableOnly = availableOnly == null ? true : availableOnly;
        this.sortedBy = sortedBy == null ? "createdAt" : sortedBy;
        this.keyword = keyword == null ? "" : keyword;
    }
}
