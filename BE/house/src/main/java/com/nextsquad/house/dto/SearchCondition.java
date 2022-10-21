package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition {
    private Boolean availableOnly;
    private String sortedBy;
    private String keyword;
}
