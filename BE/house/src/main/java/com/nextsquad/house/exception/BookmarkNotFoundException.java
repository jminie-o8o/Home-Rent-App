package com.nextsquad.house.exception;

public class BookmarkNotFoundException extends RuntimeException{
    public BookmarkNotFoundException() {
        super("해당하는 북마크를 찾을 수 없습니다.");
    }
}
