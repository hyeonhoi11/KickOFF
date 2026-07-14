package com.kickoff.kickoff.domain.team.service;

import com.kickoff.kickoff.domain.member.entity.Member;
import com.kickoff.kickoff.domain.member.repository.MemberRepository;
import com.kickoff.kickoff.domain.team.dto.MyTeamResponse;
import com.kickoff.kickoff.domain.team.dto.TeamCreateRequest;
import com.kickoff.kickoff.domain.team.dto.TeamCreateResponse;
import com.kickoff.kickoff.domain.team.dto.TeamDetailResponse;
import com.kickoff.kickoff.domain.team.dto.TeamInvitationResponse;
import com.kickoff.kickoff.domain.team.dto.TeamJoinRequest;
import com.kickoff.kickoff.domain.team.dto.TeamJoinResponse;
import com.kickoff.kickoff.domain.team.dto.TeamMemberResponse;
import com.kickoff.kickoff.domain.team.entity.Team;
import com.kickoff.kickoff.domain.team.entity.TeamInvitation;
import com.kickoff.kickoff.domain.team.entity.TeamMembership;
import com.kickoff.kickoff.domain.team.entity.TeamRole;
import com.kickoff.kickoff.domain.team.repository.TeamInvitationRepository;
import com.kickoff.kickoff.domain.team.repository.TeamMembershipRepository;
import com.kickoff.kickoff.domain.team.repository.TeamRepository;
import com.kickoff.kickoff.global.exception.AlreadyJoinedTeamException;
import com.kickoff.kickoff.global.exception.ExpiredInvitationException;
import com.kickoff.kickoff.global.exception.InvalidRequestException;
import com.kickoff.kickoff.global.exception.InvitationNotFoundException;
import com.kickoff.kickoff.global.exception.MemberNotFoundException;
import com.kickoff.kickoff.global.exception.TeamAccessDeniedException;
import com.kickoff.kickoff.global.exception.TeamNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private static final int INVITATION_CODE_LENGTH = 8;
    private static final long INVITATION_VALID_DAYS = 7;
    private static final String INVITATION_CODE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom secureRandom = new SecureRandom();

    private final TeamRepository teamRepository;
    private final TeamMembershipRepository teamMembershipRepository;
    private final TeamInvitationRepository teamInvitationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TeamCreateResponse createTeam(Long memberId, TeamCreateRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidRequestException("name은 필수입니다.");
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Team team = teamRepository.save(
                Team.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .build()
        );

        TeamMembership membership = teamMembershipRepository.save(
                TeamMembership.builder()
                        .member(member)
                        .team(team)
                        .role(TeamRole.MANAGER)
                        .build()
        );

        return TeamCreateResponse.of(team, membership.getRole());
    }

    @Transactional(readOnly = true)
    public List<MyTeamResponse> getMyTeams(Long memberId) {
        return teamMembershipRepository.findByMemberId(memberId).stream()
                .map(membership -> MyTeamResponse.of(
                        membership,
                        teamMembershipRepository.countByTeamId(membership.getTeam().getId())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamDetailResponse getTeamDetail(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
        TeamMembership membership = teamMembershipRepository.findByTeamIdAndMemberId(teamId, memberId)
                .orElseThrow(TeamAccessDeniedException::new);

        long memberCount = teamMembershipRepository.countByTeamId(teamId);
        return TeamDetailResponse.of(team, membership.getRole(), memberCount);
    }

    @Transactional(readOnly = true)
    public List<TeamMemberResponse> getTeamMembers(Long memberId, Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException();
        }
        teamMembershipRepository.findByTeamIdAndMemberId(teamId, memberId)
                .orElseThrow(TeamAccessDeniedException::new);

        return teamMembershipRepository.findByTeamId(teamId).stream()
                .map(TeamMemberResponse::from)
                .toList();
    }

    @Transactional
    public TeamInvitationResponse createInvitation(Long memberId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        TeamMembership membership = teamMembershipRepository.findByTeamIdAndMemberId(teamId, memberId)
                .orElseThrow(TeamAccessDeniedException::new);
        if (membership.getRole() != TeamRole.MANAGER) {
            throw new TeamAccessDeniedException();
        }

        TeamInvitation invitation = teamInvitationRepository.save(
                TeamInvitation.builder()
                        .team(team)
                        .code(generateUniqueInvitationCode())
                        .expiresAt(LocalDateTime.now().plusDays(INVITATION_VALID_DAYS))
                        .build()
        );

        return TeamInvitationResponse.of(invitation);
    }

    @Transactional
    public TeamJoinResponse joinTeamByInvitation(Long memberId, TeamJoinRequest request) {
        TeamInvitation invitation = teamInvitationRepository.findByCode(request.getCode())
                .orElseThrow(InvitationNotFoundException::new);

        if (invitation.isExpired()) {
            throw new ExpiredInvitationException();
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        Team team = invitation.getTeam();

        if (teamMembershipRepository.findByTeamIdAndMemberId(team.getId(), memberId).isPresent()) {
            throw new AlreadyJoinedTeamException();
        }

        TeamMembership membership = teamMembershipRepository.save(
                TeamMembership.builder()
                        .member(member)
                        .team(team)
                        .role(TeamRole.PLAYER)
                        .build()
        );

        return TeamJoinResponse.of(membership);
    }

    private String generateUniqueInvitationCode() {
        String code;
        do {
            code = generateInvitationCode();
        } while (teamInvitationRepository.existsByCode(code));
        return code;
    }

    private String generateInvitationCode() {
        StringBuilder sb = new StringBuilder(INVITATION_CODE_LENGTH);
        for (int i = 0; i < INVITATION_CODE_LENGTH; i++) {
            sb.append(INVITATION_CODE_CHARACTERS.charAt(secureRandom.nextInt(INVITATION_CODE_CHARACTERS.length())));
        }
        return sb.toString();
    }
}
