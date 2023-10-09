package com.example.howmuch.exception.notice;

import com.example.howmuch.controller.AdminController;
import com.example.howmuch.controller.EventController;
import com.example.howmuch.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {AdminController.class, EventController.class})
public class NoticeExceptionHandler {

    private static final HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(NotFoundNoticeException.class)
    public ResponseEntity<ErrorMessage> notFoundNoticeException(
            NotFoundNoticeException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }
}
