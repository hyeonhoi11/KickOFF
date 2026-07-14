package com.kickoff.kickoff.domain.team.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamCreateRequest {

    private String name;
    private String description;
}
