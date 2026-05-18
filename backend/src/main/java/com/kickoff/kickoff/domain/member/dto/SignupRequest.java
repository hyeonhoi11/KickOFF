package com.kickoff.kickoff.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
}
