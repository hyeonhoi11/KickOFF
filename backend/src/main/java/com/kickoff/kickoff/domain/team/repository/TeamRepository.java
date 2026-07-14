package com.kickoff.kickoff.domain.team.repository;

import com.kickoff.kickoff.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
