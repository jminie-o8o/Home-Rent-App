package com.nextsquad.house.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
public class SearchCondition {

    private final Boolean availableOnly;
    private final String sortedBy;
    @NotNull(message = "검색어를 입력해주세요.")
    private final String keyword;

        public SearchCondition(Boolean availableOnly, String sortedBy, String keyword) {
        this.availableOnly = availableOnly == null ? false : availableOnly;
        this.sortedBy = sortedBy == null ? "createdAt" : sortedBy;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return String.format("[availableOnly: %s, sortedBy: %s, keyword: %s]", availableOnly, sortedBy, keyword);
    }
}
