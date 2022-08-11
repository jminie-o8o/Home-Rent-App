package com.nextsquad.house.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicationCheckResponse {
    private Boolean isDuplicated;
}
