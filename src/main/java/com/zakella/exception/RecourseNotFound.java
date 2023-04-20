package com.zakella.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecourseNotFound extends RuntimeException {
    public RecourseNotFound(String message) {
        super(message);
    }
}
