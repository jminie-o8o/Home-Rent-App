package com.nextsquad.house.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.nextsquad.house.dto.GeneralResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    //TODO: 어느정도 구현 된 후 excpetion 정리해서 추가하기
    @ExceptionHandler(value = OauthClientNotFoundException.class)
    public ResponseEntity<GeneralResponseDto> handleOauthClientNotFoundException(OauthClientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDto(404, e.getMessage()));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<GeneralResponseDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDto(404, e.getMessage()));
    }

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<GeneralResponseDto> handleArticleNotFoundException(ArticleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDto(404, e.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<GeneralResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponseDto(400, e.getMessage()));
    }

    @ExceptionHandler(value = BookmarkNotFoundException.class)
    public ResponseEntity<GeneralResponseDto> handleBookmarkNotFoundException(BookmarkNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDto(404, e.getMessage()));
    }

    @ExceptionHandler(value = JWTDecodeException.class)
    public ResponseEntity<GeneralResponseDto> handleJWTDecodeException(JWTDecodeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponseDto(401, e.getMessage()));
    }
}
