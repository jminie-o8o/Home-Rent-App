package com.nextsquad.house.exception;

public class ArticleNotFoundException extends RuntimeException{
    public ArticleNotFoundException() {
        super("해당하는 게시글이 없습니다.");
    }
}
