package com.example.howmuch.exception.event;

import com.example.howmuch.exception.ErrorMessage;
import com.example.howmuch.exception.user.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = EventExceptionHandler.class)
public class EventExceptionHandler {

    private static final HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorMessage> notFoundUserException(
            NotFoundUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }
}
