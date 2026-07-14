package com.kickoff.kickoff.domain.team.controller;

import com.kickoff.kickoff.domain.team.dto.MyTeamResponse;
import com.kickoff.kickoff.domain.team.dto.TeamCreateRequest;
import com.kickoff.kickoff.domain.team.dto.TeamCreateResponse;
import com.kickoff.kickoff.domain.team.dto.TeamDetailResponse;
import com.kickoff.kickoff.domain.team.dto.TeamMemberResponse;
import com.kickoff.kickoff.domain.team.service.TeamService;
import com.kickoff.kickoff.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {

    private final TeamService teamService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<TeamCreateResponse> createTeam(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TeamCreateRequest request) {
        String token = authorizationHeader.substring(7);
        Long memberId = jwtProvider.getMemberIdFromToken(token);
        return ResponseEntity.ok(teamService.createTeam(memberId, request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<MyTeamResponse>> getMyTeams(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Long memberId = jwtProvider.getMemberIdFromToken(token);
        return ResponseEntity.ok(teamService.getMyTeams(memberId));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDetailResponse> getTeamDetail(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long teamId) {
        String token = authorizationHeader.substring(7);
        Long memberId = jwtProvider.getMemberIdFromToken(token);
        return ResponseEntity.ok(teamService.getTeamDetail(memberId, teamId));
    }

    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<TeamMemberResponse>> getTeamMembers(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long teamId) {
        String token = authorizationHeader.substring(7);
        Long memberId = jwtProvider.getMemberIdFromToken(token);
        return ResponseEntity.ok(teamService.getTeamMembers(memberId, teamId));
    }
}
