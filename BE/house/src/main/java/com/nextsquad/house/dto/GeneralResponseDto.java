package com.nextsquad.house.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralResponseDto {

    private int code;
    private String message;
}