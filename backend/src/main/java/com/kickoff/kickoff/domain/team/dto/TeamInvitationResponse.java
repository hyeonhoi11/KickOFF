package com.kickoff.kickoff.domain.team.dto;

import com.kickoff.kickoff.domain.team.entity.TeamInvitation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamInvitationResponse {

    private String code;
    private LocalDateTime expiresAt;

    public static TeamInvitationResponse of(TeamInvitation invitation) {
        return TeamInvitationResponse.builder()
                .code(invitation.getCode())
                .expiresAt(invitation.getExpiresAt())
                .build();
    }
}
