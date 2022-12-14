package com.nextsquad.house.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nextsquad.house.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(value = OauthClientNotFoundException.class)
    public ResponseEntity<GeneralResponse> handleOauthClientNotFoundException(OauthClientNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<GeneralResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
    }

    @ExceptionHandler(value = ArticleNotFoundException.class)
    public ResponseEntity<GeneralResponse> handleArticleNotFoundException(ArticleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<GeneralResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, e.getMessage()));
    }

    @ExceptionHandler(value = BookmarkNotFoundException.class)
    public ResponseEntity<GeneralResponse> handleBookmarkNotFoundException(BookmarkNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
    }

    @ExceptionHandler(value = JWTDecodeException.class)
    public ResponseEntity<GeneralResponse> handleJWTDecodeException(JWTDecodeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponse(401, e.getMessage()));
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<GeneralResponse> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponse(401, "JWT 토큰이 만료되었습니다."));
    }

    @ExceptionHandler(value = DuplicateBookmarkException.class)
    public ResponseEntity<GeneralResponse> handleDuplicateBookmarkException(DuplicateBookmarkException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, e.getMessage()));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<GeneralResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponse(401, e.getMessage()));
    }

    @ExceptionHandler(value  = MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = Optional.ofNullable(e.getBindingResult().getFieldError())
                .orElseGet(() -> new FieldError("Bad Request", "Error Field", "입력값을 확인하세요"));
        String message = fieldError.getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, message));
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<GeneralResponse> testException(BindException e) {
        FieldError fieldError = Optional.ofNullable(e.getBindingResult().getFieldError())
                .orElseGet(() -> new FieldError("Bad Request", "Error Field", "검색어를 확인하세요"));
        String message = fieldError.getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, message));
    }
}
