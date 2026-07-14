package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException() {
        super(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다.");
    }
}
