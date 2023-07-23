package com.example.howmuch.exception.notice;

import com.example.howmuch.controller.AdminController;
import com.example.howmuch.exception.ErrorMessage;
import com.example.howmuch.exception.event.NotFoundEventException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = AdminController.class)
public class NoticeExceptionHandler {

    private static final HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(NotFoundNoticeException.class)
    public ResponseEntity<ErrorMessage> notFoundNoticeException(
            NotFoundEventException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }
}
