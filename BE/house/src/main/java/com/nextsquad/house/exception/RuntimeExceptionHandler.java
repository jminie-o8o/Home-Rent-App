package com.nextsquad.house.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nextsquad.house.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@RestControllerAdvice
public class RuntimeExceptionHandler {

//    @ExceptionHandler(value = OauthClientNotFoundException.class)
//    public ResponseEntity<GeneralResponse> handleOauthClientNotFoundException(OauthClientNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
//    }
//
//    @ExceptionHandler(value = UserNotFoundException.class)
//    public ResponseEntity<GeneralResponse> handleUserNotFoundException(UserNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
//    }
//
//    @ExceptionHandler(value = ArticleNotFoundException.class)
//    public ResponseEntity<GeneralResponse> handleArticleNotFoundException(ArticleNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
//    }
//
//    @ExceptionHandler(value = IllegalArgumentException.class)
//    public ResponseEntity<GeneralResponse> handleIllegalArgumentException(IllegalArgumentException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, e.getMessage()));
//    }
//
//    @ExceptionHandler(value = BookmarkNotFoundException.class)
//    public ResponseEntity<GeneralResponse> handleBookmarkNotFoundException(BookmarkNotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponse(404, e.getMessage()));
//    }
//
////    @ExceptionHandler(value = JWTDecodeException.class)
////    public ResponseEntity<GeneralResponse> handleJWTDecodeException(JWTDecodeException e) {
////        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponse(401, e.getMessage()));
////    }
//
//    @ExceptionHandler(value = TokenExpiredException.class)
//    public ResponseEntity<GeneralResponse> handleTokenExpiredException(TokenExpiredException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponse(401, "JWT 토큰이 만료되었습니다."));
//    }
//
//    @ExceptionHandler(value = DuplicateBookmarkException.class)
//    public ResponseEntity<GeneralResponse> handleDuplicateBookmarkException(DuplicateBookmarkException e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, e.getMessage()));
//    }
//
//    @ExceptionHandler(value = AccessDeniedException.class)
//    public ResponseEntity<GeneralResponse> handleAccessDeniedException(AccessDeniedException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GeneralResponse(401, e.getMessage()));
//    }
//
//    @ExceptionHandler(value  = MethodArgumentNotValidException.class)
//    public ResponseEntity<GeneralResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        FieldError fieldError = Optional.ofNullable(e.getBindingResult().getFieldError())
//                .orElseGet(() -> new FieldError("Bad Request", "Error Field", "입력값을 확인하세요"));
//        String message = fieldError.getDefaultMessage();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400, message));
//    }

    @ExceptionHandler(value  = RuntimeException.class)
    public ResponseEntity<GeneralResponse> handleMethodArgumentNotValidException(RuntimeException e) {
        String message = e.getMessage();
        String className = e.getClass().getName();
        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder sb = new StringBuilder();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);


        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append(stackTraceElement.getMethodName());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse(400,
                String.format("message: %s\n class: %s\n stackTrace: %s\n", message, className, sw)));
    }
}
