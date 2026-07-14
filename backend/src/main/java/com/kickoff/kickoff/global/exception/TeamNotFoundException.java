package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class TeamNotFoundException extends BusinessException {

    public TeamNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 팀입니다.");
    }
}
