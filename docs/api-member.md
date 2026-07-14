# Member API 명세서

## 엔티티
Member
- id: Long (PK)
- email: String (unique)
- password: String (bcrypt 암호화)
- name: String
- phoneNumber: String (nullable)
- createdAt: LocalDateTime (자동)
- updatedAt: LocalDateTime (자동)

## API

### 1. 회원가입
POST /api/v1/members/signup

요청:
{
  "email": "hyeon@kickoff.com",
  "password": "password123!",
  "name": "김현회",
  "phoneNumber": "010-1234-5678"
}

응답 200:
{
  "memberId": 1,
  "email": "hyeon@kickoff.com",
  "name": "김현회",
  "phoneNumber": "010-1234-5678",
  "createdAt": "2026-05-17T16:00:00"
}

예외:
- 400: 이메일 형식 오류, 필수값 누락
- 409: 이미 가입된 이메일

### 2. 로그인
POST /api/v1/members/login

요청:
{
  "email": "hyeon@kickoff.com",
  "password": "password123!"
}

응답 200:
{
  "accessToken": "eyJhbGci...",
  "refreshToken": "eyJhbGci...",
  "memberId": 1,
  "name": "김현회"
}

예외:
- 401: 이메일 또는 비밀번호 불일치

### 3. 토큰 재발급
POST /api/v1/members/reissue

요청 헤더:
Authorization: Bearer {refreshToken}

응답 200:
{
  "accessToken": "eyJhbGci...",
  "refreshToken": "eyJhbGci..."
}

예외:
- 401: Refresh Token 만료 또는 유효하지 않음

### 4. 로그아웃
POST /api/v1/members/logout

요청 헤더:
Authorization: Bearer {accessToken}

응답 200:
{
  "message": "로그아웃 성공"
}

처리: Redis에서 Refresh Token 삭제

### 5. 내 정보 조회
GET /api/v1/members/me

요청 헤더:
Authorization: Bearer {accessToken}

응답 200:
{
  "memberId": 1,
  "email": "hyeon@kickoff.com",
  "name": "김현회",
  "phoneNumber": "010-1234-5678"
}

### 6. 내 정보 수정
PATCH /api/v1/members/me

요청 헤더:
Authorization: Bearer {accessToken}

요청:
{
  "name": "김현회",
  "phoneNumber": "010-9999-8888"
}

응답 200:
{
  "memberId": 1,
  "email": "hyeon@kickoff.com",
  "name": "김현회",
  "phoneNumber": "010-9999-8888"
}

예외:
- 400: 필수값 누락