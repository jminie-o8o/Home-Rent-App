package com.nextsquad.house.dto.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BookmarkRequestDto {

    @NotNull
    private Long articleId;
}
