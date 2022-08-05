//package com.nextsquad.house.exception;
//
//import com.nextsquad.house.dto.GeneralResponseDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class RuntimeExceptionHandler {
//    //TODO: 어느정도 구현 된 후 excpetion 정리해서 추가하기
//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<GeneralResponseDto> handleRuntimeException(RuntimeException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GeneralResponseDto(404, e.getMessage()));
//    }
//}
