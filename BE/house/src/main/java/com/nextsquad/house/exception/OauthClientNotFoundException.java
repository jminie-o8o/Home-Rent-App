package com.nextsquad.house.exception;

public class OauthClientNotFoundException extends RuntimeException{
    public OauthClientNotFoundException() {
        super("등록되어 있는 OAuth 서비스가 아닙니다");
    }
}
