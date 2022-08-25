package com.nextsquad.house.login.jwt;

public enum JwtTokenType {
    ACCESS(5000), // 900000
    REFRESH(604800000);

    private final int duration;

    private JwtTokenType(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
