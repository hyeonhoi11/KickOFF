package com.kickoff.kickoff.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final String KEY_PREFIX = "refresh_token:";
    private static final Duration TTL = Duration.ofDays(14);

    private final StringRedisTemplate redisTemplate;

    public void save(Long memberId, String refreshToken) {
        redisTemplate.opsForValue().set(key(memberId), refreshToken, TTL);
    }

    public Optional<String> get(Long memberId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key(memberId)));
    }

    public void delete(Long memberId) {
        redisTemplate.delete(key(memberId));
    }

    public boolean validate(Long memberId, String refreshToken) {
        return get(memberId)
                .map(stored -> stored.equals(refreshToken))
                .orElse(false);
    }

    private String key(Long memberId) {
        return KEY_PREFIX + memberId;
    }
}
