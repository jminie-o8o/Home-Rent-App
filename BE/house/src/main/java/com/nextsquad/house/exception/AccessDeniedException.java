package com.nextsquad.house.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("접근 권한이 없습니다.");
    }
}
