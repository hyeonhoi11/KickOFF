package com.kickoff.kickoff.domain.team.dto;

import com.kickoff.kickoff.domain.team.entity.TeamMembership;
import com.kickoff.kickoff.domain.team.entity.TeamRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberResponse {

    private Long memberId;
    private String name;
    private TeamRole role;
    private LocalDateTime joinedAt;

    public static TeamMemberResponse from(TeamMembership membership) {
        return TeamMemberResponse.builder()
                .memberId(membership.getMember().getId())
                .name(membership.getMember().getName())
                .role(membership.getRole())
                .joinedAt(membership.getJoinedAt())
                .build();
    }
}
