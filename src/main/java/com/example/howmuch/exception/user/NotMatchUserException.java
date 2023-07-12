package com.example.howmuch.exception.user;


public class NotMatchUserException extends RuntimeException {
    public NotMatchUserException(String message) {
        super(message);
    }
}
