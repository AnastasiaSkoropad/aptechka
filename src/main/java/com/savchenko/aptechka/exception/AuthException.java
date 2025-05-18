package com.savchenko.aptechka.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthException extends RuntimeException {
    private final String code;

    public AuthException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AuthException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
