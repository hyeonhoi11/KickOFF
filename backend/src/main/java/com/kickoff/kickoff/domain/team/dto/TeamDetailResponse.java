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
public class TeamDetailResponse {

    private Long teamId;
    private String name;
    private String description;
    private TeamRole myRole;
    private long memberCount;
    private LocalDateTime createdAt;

    public static TeamDetailResponse of(Team team, TeamRole myRole, long memberCount) {
        return TeamDetailResponse.builder()
                .teamId(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .myRole(myRole)
                .memberCount(memberCount)
                .createdAt(team.getCreatedAt())
                .build();
    }
}
