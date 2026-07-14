package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class AlreadyJoinedTeamException extends BusinessException {

    public AlreadyJoinedTeamException() {
        super(HttpStatus.CONFLICT, "이미 소속된 팀입니다.");
    }
}
