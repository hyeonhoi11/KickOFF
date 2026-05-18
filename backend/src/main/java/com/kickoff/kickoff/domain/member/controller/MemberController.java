package com.kickoff.kickoff.domain.member.controller;

import com.kickoff.kickoff.domain.member.dto.*;
import com.kickoff.kickoff.domain.member.service.MemberService;
import com.kickoff.kickoff.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(memberService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(memberService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberInfoResponse> getMyInfo(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Long memberId = jwtProvider.getMemberIdFromToken(token);
        return ResponseEntity.ok(memberService.getMyInfo(memberId));
    }

    @PostMapping("/reissue")
    public ResponseEntity<LoginResponse> reissue(
            @RequestHeader("Authorization") String authorizationHeader) {
        String refreshToken = authorizationHeader.substring(7);
        return ResponseEntity.ok(memberService.reissue(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.substring(7);
        memberService.logout(accessToken);
        return ResponseEntity.ok().build();
    }
}
