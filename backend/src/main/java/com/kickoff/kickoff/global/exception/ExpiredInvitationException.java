package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class ExpiredInvitationException extends BusinessException {

    public ExpiredInvitationException() {
        super(HttpStatus.BAD_REQUEST, "만료된 초대 코드입니다.");
    }
}
