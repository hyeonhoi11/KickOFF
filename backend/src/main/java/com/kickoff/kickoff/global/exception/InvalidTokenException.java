package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
    }
}
