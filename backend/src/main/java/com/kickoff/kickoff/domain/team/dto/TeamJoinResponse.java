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
public class TeamJoinResponse {

    private Long teamId;
    private String name;
    private TeamRole myRole;
    private LocalDateTime joinedAt;

    public static TeamJoinResponse of(TeamMembership membership) {
        return TeamJoinResponse.builder()
                .teamId(membership.getTeam().getId())
                .name(membership.getTeam().getName())
                .myRole(membership.getRole())
                .joinedAt(membership.getJoinedAt())
                .build();
    }
}
