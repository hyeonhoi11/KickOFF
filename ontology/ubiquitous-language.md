# KickOFF 유비쿼터스 언어

## 규칙
이 프로젝트의 모든 코드, 변수명, API 경로, 주석, 커밋 메시지에서
아래 표의 '사용' 표현만 쓴다. '금지' 표현은 절대 사용하지 않는다.

## 용어 정의

| 사용 | 금지 | 설명 |
|------|------|------|
| Event | 일정, Schedule | Match와 Training의 공통 추상 단위 |
| Match | 경기 일정, Game | 상대팀과의 경기 |
| Training | 훈련 일정, Practice | 팀 내부 훈련 |
| Member | 유저, User, 선수 | KickOFF 사용자 |
| Manager | 관리자, Admin | 팀 내 관리 권한 보유자 |
| Player | 선수, 팀원 | 일반 팀원 |
| TeamMembership | 팀원 관계, 멤버십 | Member ↔ Team 소속 관계 |
| TeamInvitation | 초대장, 가입 코드 | 팀 초대 코드 |
| Participation | 출석, Attendance | Member의 Event 참여 상태 |
| Notice | 공지사항, 게시글 | 팀 공지 |
| Comment | 댓글, Reply | Notice에 달리는 응답 |

## API 경로 규칙
- /members
- /teams
- /teams/{teamId}/events
- /teams/{teamId}/events/{eventId}/participations
- /teams/{teamId}/notices
- /teams/{teamId}/notices/{noticeId}/comments
- /teams/{teamId}/invitations