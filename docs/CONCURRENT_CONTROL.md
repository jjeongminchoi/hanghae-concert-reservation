# [동시성 제어 보고서]

---

## 1. 콘서트 예약 시 발생할 수 있는 동시성 이슈

### 1.1 유저 포인트 잔액
- **시나리오**
    - 유저가 포인트 충전 또는 결제 시 중복 요청
- **이슈**
    - 중복 요청에 대한 동시성 관리가 되지 않으면 포인트가 중복으로 충전/사용되어 잔액 관리에 문제가 발생
- **범위 및 대상**
    - 충전 서비스 / 요청 유저의 UserPoint Entity
- **대응 전략**
    - Pessimistic Lock (x-lock)
- **의사 결정**
    - 유저가 본인의 포인트에만 접근하므로 중복 요청이 자주 발생할 가능성은 낮지만, </br>
      포인트 증/차감은 민감도가 높은 작업이므로 모든 요청에 대해 정합성이 보장 되어야 한다. </br>
      따라서 Pessimistic Lock(X-Lock)을 사용하여 다른 트랜잭션이 접근하지 못하게 하여 일관성을 유지한다.

### 1.2 좌석 예약
- **시나리오**
    - 다수의 유저가 하나의 콘서트 좌석에 대해 동시에 예약 요청
- **이슈**
    - 한 좌석에 여러 명의 유저가 동시에 예약을 시도하면 중복 예약이 발생하는 문제 발생
- **범위 및 대상**
    - 예약 서비스 / ConcertSeat Entity
- **대응 전략**
    - Retry하지 않는 Optimistic Lock
- **의사 결정**
    - 특정 좌석에 대한 예약 경합이 빈번하게 발생할 수 있으며, 누군가 선점한 좌석에 대해 재예약 시도가 불필요한 상황임. </br>
      따라서 Optimistic Lock을 사용하여 예약 시도 시 충돌이 발생하면 실패로 처리하고,
      재시도를 수행하지 않음으로써 성능 저하를 방지함.

---

## 2. 동시성 제어 방식 비교

### 2.1 Application Lock

- `Pessimistic Lock`
    1. LockModeType.PESSIMISTIC_READ (s-lock)

       : 트랜잭션이 데이터를 읽는 동안 읽기 잠금으로 다른 트랜잭션에서 해당 데이터를 수정할 수 없도록 잠궈 데이터의 일관성을 보장하는 방식

        - 장점
            - 읽기 트랜잭션에 대한 데이터 일관성 보장
            - 충돌 가능성이 높은 상황에서 무결성을 쉽게 유지
        - 단점
            - 공유 자원에 대한 대량의 읽기 트랜잭션에서 성능 저하 발생 가능
        - 적용 사례
            - 은행 시스템에서 계좌 잔액 조회와 같이 일관된 데이터가 필요한 경우, 특히 읽기 작업이 일관된 결과를 요구하는 경우.
    2. LockModeType.PESSIMISTIC_WRITE (x-lock)

       트랜잭션이 데이터를 수정할 동안 다른 트랜잭션이 해당 데이터에 접근하지 못하도록 잠금. 충돌을 완전히 방지하는 방식으로, 업데이트와 관련된 중요한 데이터의 무결성을 보장하는 제어 방식.

        - 장점
            - 무결성 보장하고 동시 수정 작업이 발생하지 않도록 보장한다.
        - 단점
            - 동시에 많은 요청이 존재할 때 대기 시간이 길어지고, 특히 높은 빈도의 읽기 트랜잭션은 성능 저하를 유발할 수 있다.
        - 적용 사례
            - 재고 관리 시스템, 결제 시스템 등에서 충돌 방지가 필수적인 데이터 수정 작업에 사용
- `Optimistic Lock`

  : DB에 직접적인 Lock을 걸지 않고 레코드별 version을 관리하는 방식으로,
  읽기에 자유롭고 쓰기 시점에 version 충돌 유무로 변경을 감지하는 동시성 제어 방식

    - 장점
        - 성능이 우수하며 잠금 비용이 낮아 다수의 읽기 트랜잭션과 잘 어울림.
        - 충돌이 적을 상황에 잘 어울림
        - 충돌이 빈번하더라도 재시도 로직이 필요 없다면 적합한 방식이 될 수 있다.
    - 단점
        - 충돌 발생 시 재시도 비용이 클 경우 적합하지 않을 수 있음.

          (단점을 보완하기 위해 재시도 로직은 최대 2번으로 제한하는 것도 방법)

    - 적용 사례
        - 유저 정보 수정, 상품 정보 수정 등 충돌 가능성이 낮고 데이터 무결성 관리가 중요한 경우

### 2.2 RDBMS Lock

Application Lock에 JPA에서 RDBMS의 s-lock과 x-lock을 활용해 DB Lock 적용하는 것이므로 Pessimistic Lock 내용과 동일

- `s-lock` (select … for share)
- `x-lock` (select … for update)


### 2.3 Distributed Lock

- Redis
    1. `Simple Lock`

       : Redis의 setnx 명령을 통해 락을 생성해 특정 키에 락을 설정하고 해제하는 방식

        - 장점
            - 구현이 간단하고 락 사용 비용이 저렴하다.
        - 단점
            - 분산 환경에서 노드 장애 발생 시 락이 자동 해제되지 않거나 잘못된 상태로 남을 위험이 있음.
        - 적용 사례
            - 단일 Redis 노드에서의 간단한 동시성 제어. 주로 짧은 시간 동안의 자원 접근을 제어할 때 적합.
    2. `Spin Lock`

       : Lettuce 클라이언트를 통해 일정 시간 동안 락을 유지하며 반복적으로 락 획득을 시도하는 방식입니다.

        - 장점
            - 구현이 쉽다
            - Redis 클러스터 환경에서 성능이 우수하며 자원 회수가 빨라 응답 속도가 빠르다.
        - 단점
            - 락 획득 재시도가 필요한 경우, 반복적인 연결 요청으로 인해 Redis에 부하가 증가할 수 있다.
        - 적용 사례
            - 다수의 서비스가 짧은 시간 동안 반복적으로 접근하는 데이터에 대한 락 제어.
    3. `pub/sub`

       : Redisson을 사용해 pub/sub을 활용한 분산 락을 구현합니다. Redis를 통해 메시지를 전달하여 락 획득 요청을 관리합니다.

        - 장점
            - 분산 시스템 환경에서 락 획득을 효율적으로 관리할 수 있으며, 락 해제와 노드 간 커뮤니케이션이 원활하다.
        - 단점
            - Redis 설정 및 Redisson 의존성 추가 등 초기 설정이 복잡하며 관리 부담이 발생할 수 있다.
            - 메시지 손실 가능성이 존재 한다.
        - 적용 사례
            - 분산 환경에서 여러 서비스가 공통 자원에 대해 락이 필요할 때,
              특히 다수의 분산 서버에서 하나의 자원을 공유할 때

## 3. 성능 비교

### 3.1 환경
- OS: macOS Sonoma 14.6.1
- CPU: Apple M1, 8-core
- Memory: 8GB LPDDR4X
- Storage: 256GB SSD
- thread pool: 16(8 * 2)

### 3.2 시나리오
- 하나의 좌석에 대해 200개 동시 요청 진행
- 대기열 상태를 고려해 최대 200개 동시 요청 가능
    - 5분마다 대기열 100개 활성 및 만료시간 10분
    - 10분마다 대기열 비활성
    - 오차 범위에 따라 100~200개 대기열 활성 예상
        - 5분 -> 100개
        - 10분 -> 100+100-0~100 = 100~200개
        - 15분 -> 100+100+100-100~200 = 100~200개
        - ...

### 3.3 성능 비교

- 비관적 락
  - 전체 테스트 수행 시간: 133ms
  - 최소 작업 수행 시간: 0ms
  - 최대 작업 수행 시간: 78ms
  - 평균 작업 수행 시간: 10.14ms
- 낙관적 락
  - 전체 테스트 수행 시간: 99ms
  - 최소 작업 수행 시간: 0ms
  - 최대 작업 수행 시간: 86ms
  - 평균 작업 수행 시간: 7.625ms
- 분산 락
  - 전체 테스트 수행 시간: 415ms
  - 최소 작업 수행 시간: 1ms
  - 최대 작업 수행 시간: 118ms
  - 평균 작업 수행 시간: 32.33ms
