package com.nextsquad.house.dto.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkRequestDto {
    private Long userId;
    private Long articleId;
}
