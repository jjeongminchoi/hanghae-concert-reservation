# 콘서트 예약 서비스 API 명세

---

## 1. 대기열 토큰 발급

- **URL**: `POST /api/v1/waiting-queue`
- **설명**: 사용자가 대기열에 추가되며, 콘서트별로 토큰을 발급받습니다. 

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
  - `SESSION_ID`: (string) 세션 ID

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  { 
    "waitingQueueUuid": "uuuuuuuuiiiidddd"
  }
  ```

---

## 2. 대기열 토큰 조회

- **URL**: `GET /api/v1/waiting-queue`
- **설명**: 대기열에서 사용자 토큰을 조회합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
  - `WAITING-QUEUE-UUID` (string): 대기열 토큰

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  {
    "id": 1,
    "userId": 1,
    "status": "EXPIRE",
    "createdAt": "2024-10-10 09:00:00",
    "expiredAt": "2024-10-10 09:20:00",
    "updatedAt": "2024-10-10 09:30:00"
  }
  ```

---

## 3. 예약 가능한 콘서트 일정 조회

- **URL**: `GET /api/v1/concerts/{concertId}/schedules`
- **설명**: 예약 가능한 콘서트 날짜를 조회합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
  - `WAITING-QUEUE-UUID` (string): 대기열 토큰
- **Path Variable**
  - concertId (int): 콘서트의 고유 ID (필수)
- **Query Parameters**
  - status (ConcertScheduleStatus): 콘서트일정상태 (OPEN, CLOSE) (필수)

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  {
    "concerts": [
      {
        "concertScheduleId": 1,
        "concertId": 1,
        "date": "2024-10-10 09:00:00",
        "venue": "장소A"
      },
      {
        "concertScheduleId": 2,
        "concertId": 1,
        "date": "2024-10-15 09:00:00",
        "venue": "장소B"
      }
    ]
  }
  ```

---

## 4. 콘서트 좌석 조회

- **URL**: `GET /api/v1/concerts/{concertId}/schedules/{scheduleId}/seats`
- **설명**: 특정 날짜의 콘서트 좌석을 조회합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
  - `WAITING-QUEUE-UUID` (string): 대기열 토큰
- **Path Variable**
  - concertId (int): 예약할 콘서트의 고유 ID (필수)
  - scheduleId (int): 예약할 콘서트 일정의 고유 ID (필수)

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  {
    "availableSeats": [
      {
        "concertSeatId": 10,
        "seatNumber": 1,
        "price": 10000,
        "status": "AVAILABLE"
      },
      {
        "concertSeatId": 11,
        "seatNumber": 2,
        "price": 10000,
        "status": "AVAILABLE"
      }
    ],
    "unAvailableSeats": [
      {
        "concertSeatId": 12,
        "seatNumber": 3,
        "price": 10000,
        "status": "RESERVE"
      },
      {
        "concertSeatId": 13,
        "seatNumber": 4,
        "price": 10000,
        "status": "TEMP_RESERVE"
      }
    ]
  }
  ```

---

## 5. 좌석 (임시)예약 요청

- **URL**: `POST /api/v1/concerts/{concertId}/schedules/{scheduleId}/reservations`
- **설명**: 좌석을 (임시)예약 합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
  - `WAITING-QUEUE-UUID` (string): 대기열 토큰
- **Path Variable**
  - concertId (int): 예약할 콘서트의 고유 ID (필수)
  - concertDateId (int): 예약할 콘서트 일정의 고유 ID (필수)
- **Request Body**:
  ```json
  {
    "userId": 1,
    "concertSeatId": 12
  }
  ```

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  {
    "reservationId": 1
  }
  ```

---

## 6. 결제

- **URL**: `POST /api/v1/payment`
- **설명**: 예약된 콘서트를 결제합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
  - `WAITING-QUEUE-UUID` (string): 대기열 토큰
- **Request Body**:
  ```json
  {
    "userId": 1,
    "reservationId": 100
  }
  ```

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  {
    "paymentId": 1000
  }
  ```

---

## 7. 유저 포인트 충전

- **URL**: `POST /api/v1/users/{userId}/points`
- **설명**: 사용자의 잔액을 충전합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
- **Path Variable**
  - userId (int): 예약할 콘서트의 고유 ID (필수)
- **Request Body**:
  ```json
  {
    "userId": 1, 
    "amount": 10000
  }
  ```

### 응답

- **Status Code**: `200 OK`

---

## 8. 유저 포인트 조회

- **URL**: `GET /api/v1/users/{userId}/points`
- **설명**: 사용자의 잔액을 조회합니다.

### 요청

- **Headers**:
  - `Content-Type`: `application/json`
- **Path Variable**
  - userId (int): 사용자의 고유 ID (필수)

### 응답

- **Status Code**: `200 OK`
- **Response Body**:
  ```json
  {
    "userId": 1,
    "balance": 10000
  }
  ```