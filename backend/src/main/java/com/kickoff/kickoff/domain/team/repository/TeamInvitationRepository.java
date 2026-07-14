package com.kickoff.kickoff.domain.team.repository;

import com.kickoff.kickoff.domain.team.entity.TeamInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {

    Optional<TeamInvitation> findByCode(String code);

    boolean existsByCode(String code);
}
