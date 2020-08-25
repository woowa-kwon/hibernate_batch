# 하이버네이트 배치 확인용 테스트 코드

- 도커가 설치되어 있는 경우에는 현재 프로젝트 루트에서 아래 명령어를 실행해 주세요.
```shell script
docker-compose up
```
- 도커가 설치되어 있지 않은 경우에는 `hibernate_batch` 데이터 베이스를 생성후 `application.yml` 파일의 `username` 과 `password` 를 수정해 주세요.
```yaml
spring:
  datasource:
    hikari:
      username: root
      password: root
```
