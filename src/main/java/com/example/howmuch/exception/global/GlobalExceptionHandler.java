package com.example.howmuch.exception.global;

import com.example.howmuch.exception.ErrorMessage;
import com.example.howmuch.exception.ErrorMessage.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


/**
 * 전역적으로 예외 처리를 하는 클래스
 * - ResponseEntityExceptionHandler = 스프링 예외를 미치 처리해둔 추상 클래스
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(
            IllegalArgumentException exception
    ) {
        log.warn("IllegalArgumentException Occurs");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    /**
     * Method Argument 유효성 실패 예외
     **/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        log.warn("MethodArgumentNotValidException Occurs");
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(ValidationError::of)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST, validationErrors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> allExceptionHandler(
            Exception exception
    ) {
        log.warn("Exception Occurs");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
