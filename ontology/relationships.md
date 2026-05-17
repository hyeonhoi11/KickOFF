# KickOFF 도메인 관계 정의

## 관계 다이어그램

Member ──────────── TeamMembership ──────────── Team
                    (role, joinedAt)              │
                                                  │
                                              Event (abstract)
                                             /         \
                                          Match      Training
                                            │
                                      Participation
                                      (Member ↔ Event)

Team ──── Notice ──── Comment
           (MANAGER)   (Member)

Team ──── TeamInvitation

## 관계 상세

| From | To | 관계 | 비고 |
|------|----|------|------|
| Member | Team | N:M | TeamMembership 통해 |
| Team | Event | 1:N | |
| Event | Participation | 1:N | Event 생성 시 자동 생성 |
| Member | Participation | 1:N | |
| Team | Notice | 1:N | |
| Notice | Comment | 1:N | |
| Team | TeamInvitation | 1:N | |
| Event | Match | 상속 | JOINED 전략 |
| Event | Training | 상속 | JOINED 전략 |

## 데이터 흐름

### Event 생성 시
1. MANAGER가 Event 생성
2. 해당 Team의 전체 Member 조회
3. 각 Member에 대해 Participation 자동 생성 (상태: PENDING)

### TeamInvitation 사용 시
1. Member가 초대 코드 입력
2. 유효한 TeamInvitation 확인
3. TeamMembership 생성 (role: PLAYER)
4. Member가 Team에 합류