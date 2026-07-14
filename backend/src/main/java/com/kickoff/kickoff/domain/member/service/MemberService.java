package com.kickoff.kickoff.domain.member.service;

import com.kickoff.kickoff.domain.member.dto.*;
import com.kickoff.kickoff.domain.member.entity.Member;
import com.kickoff.kickoff.domain.member.repository.MemberRepository;
import com.kickoff.kickoff.global.exception.DuplicateEmailException;
import com.kickoff.kickoff.global.exception.InvalidCredentialsException;
import com.kickoff.kickoff.global.exception.InvalidTokenException;
import com.kickoff.kickoff.global.exception.MemberNotFoundException;
import com.kickoff.kickoff.global.jwt.JwtProvider;
import com.kickoff.kickoff.global.redis.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return SignupResponse.from(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String accessToken = jwtProvider.generateAccessToken(member.getId(), member.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(member.getId());
        refreshTokenService.save(member.getId(), refreshToken);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getId())
                .name(member.getName())
                .build();
    }

    @Transactional
    public LoginResponse reissue(String refreshToken) {
        Long memberId = jwtProvider.getMemberIdFromToken(refreshToken);
        if (!refreshTokenService.validate(memberId, refreshToken)) {
            throw new InvalidTokenException();
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        String newAccessToken = jwtProvider.generateAccessToken(member.getId(), member.getEmail());
        String newRefreshToken = jwtProvider.generateRefreshToken(member.getId());
        refreshTokenService.save(member.getId(), newRefreshToken);
        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .memberId(member.getId())
                .name(member.getName())
                .build();
    }

    public void logout(String accessToken) {
        Long memberId = jwtProvider.getMemberIdFromToken(accessToken);
        refreshTokenService.delete(memberId);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return MemberInfoResponse.from(member);
    }
}
