# KickOFF 도메인 개념 정의

## 핵심 엔티티

### Event (추상)
Match와 Training의 공통 단위. 직접 생성 불가.
- 공통 속성: 날짜/시간, 장소, 상태(SCHEDULED/COMPLETED/CANCELLED)
- 공통 관계: Participation, Team
- JPA 상속 전략: JOINED

### Match
상대팀과의 경기. Event를 상속.
- 고유 속성: 상대팀명, 홈/어웨이, 최종 스코어
- 제약: 스코어는 COMPLETED 상태일 때만 유효 (R09)

### Training
팀 내부 훈련. Event를 상속.
- 고유 속성: 훈련 메모

### Team
조직 단위. 모든 데이터의 소속 기준.
- 관계: Member(N:M, TeamMembership 통해), Event, Notice, TeamInvitation

### Member
KickOFF 사용자.
- 관계: Team(1개 이상, N:M), TeamMembership, Participation

### TeamMembership
Member ↔ Team 소속 관계.
- 속성: 역할(MANAGER/PLAYER), 가입일
- 제약: Team별 역할 독립 부여 (R10)

### TeamInvitation
팀 초대 코드.
- 제약: 사용 시 PLAYER 역할로 자동 합류 (R11)

### Participation
Member의 Event 참여 상태.
- 상태: PENDING(기본) / ATTEND / ABSENT
- 제약: Event 생성 시 Team 전체 Member에게 자동 생성 (R02)

### Notice
팀 공지. MANAGER만 작성 가능 (R06).

### Comment
Notice에 달리는 응답.

## 제약 규칙
R02: Participation은 Event 생성 시 자동 생성 (PENDING)
R06: MANAGER만 Event/Notice 생성·수정·삭제 가능
R07: Member는 하나 이상의 Team에 속할 수 있음 (N:M)
R09: 스코어는 Match 상태가 COMPLETED일 때만 유효
R10: Team 내 역할(MANAGER/PLAYER)은 Team별로 독립 부여
R11: TeamInvitation 사용 시 PLAYER 역할로 자동 합류