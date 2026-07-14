package com.kickoff.kickoff.domain.team.dto;

import com.kickoff.kickoff.domain.team.entity.Team;
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
public class TeamCreateResponse {

    private Long teamId;
    private String name;
    private String description;
    private TeamRole myRole;
    private LocalDateTime createdAt;

    public static TeamCreateResponse of(Team team, TeamRole myRole) {
        return TeamCreateResponse.builder()
                .teamId(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .myRole(myRole)
                .createdAt(team.getCreatedAt())
                .build();
    }
}
