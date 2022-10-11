package com.nextsquad.house.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ImageResponse {

    private List<String> images;

    public ImageResponse(List<String> images) {
        this.images = images;
    }
}
