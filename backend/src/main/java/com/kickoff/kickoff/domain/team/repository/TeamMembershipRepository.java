package com.kickoff.kickoff.domain.team.repository;

import com.kickoff.kickoff.domain.team.entity.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {

    List<TeamMembership> findByMemberId(Long memberId);

    List<TeamMembership> findByTeamId(Long teamId);

    Optional<TeamMembership> findByTeamIdAndMemberId(Long teamId, Long memberId);

    long countByTeamId(Long teamId);
}
