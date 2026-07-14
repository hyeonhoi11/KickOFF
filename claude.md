# KickOFF 프로젝트 컨텍스트

## 프로젝트 개요
대학교 및 사회인 축구부 통합 관리 앱
- Backend: Spring Boot 3.4.5, Java 17, PostgreSQL
- Frontend: React Native (Expo)
- 목표: 실제 앱스토어 출시

## 디렉토리 구조
- ontology/ : 도메인 개념 정의 (개발 전 반드시 참고)
- .claude/ : 작업 규칙 및 스킬 정의
- backend/ : Spring Boot 프로젝트
- frontend/ : React Native 프로젝트
- docs/ : 아키텍처 문서

## 핵심 규칙
1. 코드 작성 전 ontology/domain-concepts.md를 반드시 참고할 것
2. 유비쿼터스 언어를 반드시 준수할 것 (ontology/ubiquitous-language.md 참고)
3. 한 번의 작업 단위는 하나의 도메인, 하나의 레이어
4. 작업 완료 후 claude-progress.txt에 진행 상황 기록
5. Claude Code는 반드시 프로젝트 루트(KickOFF/)에서 실행한다. backend/ 등 하위 디렉토리에서 실행하면 .claude/ 에이전트와 docs/ 명세서를 인식하지 못한다.

## 금지 표현
- User → Member 사용
- Schedule → Event 사용
- Game → Match 사용
- Practice → Training 사용
- Admin → Manager 사용
- Attendance → Participation 사용

## Git 규칙
- main: 배포 브랜치 (직접 push 금지)
- develop: 개발 통합 브랜치
- feat/{기능명}: 기능 브랜치
- 커밋 prefix: [feat] [fix] [docs] [refactor] [test]