package com.nextsquad.house.exception;

public class DuplicateBookmarkException extends RuntimeException {
    public DuplicateBookmarkException() {
        super("이미 북마크에 추가된 글입니다.");
    }
}
