package com.kickoff.kickoff.domain.member.dto;

import com.kickoff.kickoff.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {

    private Long memberId;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDateTime createdAt;

    public static SignupResponse from(Member member) {
        return SignupResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
