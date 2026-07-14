package com.kickoff.kickoff.global.exception;

import org.springframework.http.HttpStatus;

public class InvitationNotFoundException extends BusinessException {

    public InvitationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 초대 코드입니다.");
    }
}
