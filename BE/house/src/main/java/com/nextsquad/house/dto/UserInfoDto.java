package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String displayName;
    private String profileImageUrl;
    private String gender;
}
