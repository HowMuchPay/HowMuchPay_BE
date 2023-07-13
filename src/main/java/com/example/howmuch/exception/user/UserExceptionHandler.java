package com.example.howmuch.exception.user;

import com.example.howmuch.controller.KakaoOauthController;
import com.example.howmuch.controller.UserController;
import com.example.howmuch.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {UserController.class, KakaoOauthController.class})
public class UserExceptionHandler {

    private static final HttpStatus errorStatus = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(ForbiddenUserException.class)
    public ResponseEntity<ErrorMessage> forbiddenUserException(
            ForbiddenUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> invalidTokenException(
            InvalidTokenException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ErrorMessage> notFoundUserException(
            NotFoundUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(NotMatchUserException.class)
    public ResponseEntity<ErrorMessage> notMatchUserException(
            NotMatchUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorMessage> unauthorizedUserException(
            UnauthorizedUserException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, errorStatus));
    }
}
