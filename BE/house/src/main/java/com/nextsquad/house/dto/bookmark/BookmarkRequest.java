package com.nextsquad.house.dto.bookmark;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkRequest {

    @NotNull
    private Long articleId;
}
