package com.nextsquad.house.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ImageResponseDto {

    private List<String> images;

    public ImageResponseDto(List<String> images) {
        this.images = images;
    }
}
