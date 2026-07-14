package com.kickoff.kickoff.domain.member.dto;

import com.kickoff.kickoff.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {

    private Long memberId;
    private String email;
    private String name;
    private String phoneNumber;

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
