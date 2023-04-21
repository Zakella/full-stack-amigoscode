package com.zakella.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateRecourseException extends RuntimeException{
    public DuplicateRecourseException(String message) {
        super(message);
    }
}
