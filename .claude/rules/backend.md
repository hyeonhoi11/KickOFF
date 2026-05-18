# Backend 개발 규칙

## 엔티티 필드
엔티티 필드는 반드시 docs/의 API 명세서 기준으로만 작성한다. 명세서에 없는 필드는 임의로 추가하지 않는다.

## 배포 전 체크리스트
- application.yml의 JWT secret은 환경변수로 분리할 것 (${JWT_SECRET})
- DB 비밀번호는 환경변수로 분리할 것 (${DB_PASSWORD})
