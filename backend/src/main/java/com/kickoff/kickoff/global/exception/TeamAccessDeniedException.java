package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class TeamAccessDeniedException extends BusinessException {

    public TeamAccessDeniedException() {
        super(HttpStatus.FORBIDDEN, "해당 팀 소속이 아닙니다.");
    }
}
