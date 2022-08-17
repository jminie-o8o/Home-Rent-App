package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionDto {
    private Boolean isCompleted;
    private String sortedBy;
    private String keyword;
}
