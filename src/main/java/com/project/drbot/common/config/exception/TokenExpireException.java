package com.project.drbot.common.config.exception;

public class TokenExpireException extends RuntimeException {
    public TokenExpireException() {
        super();
    }

    public TokenExpireException(String message) {
        super(message);
    }
}
