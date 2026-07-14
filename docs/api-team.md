    # Team API 명세서

## 엔티티

Team
- id: Long (PK)
- name: String
- description: String (nullable)
- createdAt: LocalDateTime (자동)
- updatedAt: LocalDateTime (자동)

TeamMembership
- id: Long (PK)
- member: Member (FK, ManyToOne)
- team: Team (FK, ManyToOne)
- role: Enum (MANAGER / PLAYER)
- joinedAt: LocalDateTime (자동)
- 제약: (member_id, team_id) unique — 같은 팀에 중복 소속 불가

TeamInvitation
- id: Long (PK)
- team: Team (FK, ManyToOne)
- code: String (unique, 랜덤 8자 영대문자+숫자)
- expiresAt: LocalDateTime (발급 시점 + 7일)
- createdAt: LocalDateTime (자동)

## 공통
- 모든 API는 Authorization: Bearer {accessToken} 필요
- 인증 실패 시 401

## API

### 1. 팀 생성
POST /api/v1/teams

요청:
{
  "name": "ASCII FC",
  "description": "소융대 축구팀"
}

응답 200:
{
  "teamId": 1,
  "name": "ASCII FC",
  "description": "소융대 축구팀",
  "myRole": "MANAGER",
  "createdAt": "2026-07-14T10:00:00"
}

처리: 생성자에 대한 TeamMembership(role=MANAGER) 자동 생성

예외:
- 400: name 누락

### 2. 내 팀 목록 조회
GET /api/v1/teams/my

응답 200:
[
  {
    "teamId": 1,
    "name": "ASCII FC",
    "myRole": "MANAGER",
    "memberCount": 12,
    "joinedAt": "2026-07-14T10:00:00"
  }
]

### 3. 팀 상세 조회
GET /api/v1/teams/{teamId}

응답 200:
{
  "teamId": 1,
  "name": "ASCII FC",
  "description": "소융대 축구팀",
  "myRole": "MANAGER",
  "memberCount": 12,
  "createdAt": "2026-07-14T10:00:00"
}

예외:
- 404: 존재하지 않는 팀
- 403: 해당 팀 소속이 아님

### 4. 팀 멤버 목록 조회
GET /api/v1/teams/{teamId}/members

응답 200:
[
  {
    "memberId": 1,
    "name": "김현회",
    "role": "MANAGER",
    "joinedAt": "2026-07-14T10:00:00"
  }
]

예외:
- 404: 존재하지 않는 팀
- 403: 해당 팀 소속이 아님

### 5. 초대 코드 발급
POST /api/v1/teams/{teamId}/invitations

응답 200:
{
  "code": "A3K9X2P7",
  "expiresAt": "2026-07-21T10:00:00"
}

예외:
- 404: 존재하지 않는 팀
- 403: MANAGER가 아님 (R06)

### 6. 초대 코드로 팀 합류
POST /api/v1/teams/invitations/join

요청:
{
  "code": "A3K9X2P7"
}

응답 200:
{
  "teamId": 1,
  "name": "ASCII FC",
  "myRole": "PLAYER",
  "joinedAt": "2026-07-14T11:00:00"
}

처리: TeamMembership(role=PLAYER) 생성 (R11)

예외:
- 404: 존재하지 않는 초대 코드
- 400: 만료된 초대 코드
- 409: 이미 소속된 팀

## 추가 예외 클래스 (global/exception/)
- TeamNotFoundException → 404
- TeamAccessDeniedException → 403
- InvitationNotFoundException → 404
- ExpiredInvitationException → 400
- AlreadyJoinedTeamException → 409