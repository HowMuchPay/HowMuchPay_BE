package com.example.howmuch.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private final int code;
    private final String errorSimpleName;
    private final String msg;
    private final LocalDateTime timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationError> errors;

    public ErrorMessage(Exception exception, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.errorSimpleName = exception.getClass().getSimpleName();
        this.msg = exception.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorMessage(Exception exception, HttpStatus httpStatus, List<ValidationError> errors) {
        this.code = httpStatus.value();
        this.errorSimpleName = exception.getClass().getSimpleName();
        this.msg = exception.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }

    public static ErrorMessage of(Exception exception, HttpStatus httpStatus) {
        return new ErrorMessage(exception, httpStatus);
    }

    public static ErrorMessage of(Exception exception, HttpStatus httpStatus, List<ValidationError> errors) {
        return new ErrorMessage(exception, httpStatus, errors);
    }


    /**
     * - 데이터 바인딩 오류을 위한 정적 이너 클래스
     **/
    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {
        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
