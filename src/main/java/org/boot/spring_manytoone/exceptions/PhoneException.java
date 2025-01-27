package org.boot.spring_manytoone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneException extends IOException {
    public PhoneException(String message) {
        super(message);
    }
}
