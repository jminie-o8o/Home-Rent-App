package com.nextsquad.house.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("해당하는 ID의 사용자가 존재하지 않습니다.");
    }
}
