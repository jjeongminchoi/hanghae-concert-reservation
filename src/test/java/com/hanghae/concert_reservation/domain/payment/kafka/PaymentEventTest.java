package com.hanghae.concert_reservation.domain.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.concert_reservation.adapter.api.payment.dto.response.PaymentResponse;
import com.hanghae.concert_reservation.application.payment.usecase.ConcertPaymentUseCase;
import com.hanghae.concert_reservation.config.DatabaseCleanUp;
import com.hanghae.concert_reservation.domain.concert.entity.Concert;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSchedule;
import com.hanghae.concert_reservation.domain.concert.entity.ConcertSeat;
import com.hanghae.concert_reservation.domain.concert.entity.Reservation;
import com.hanghae.concert_reservation.domain.payment.dto.command.PaymentCommand;
import com.hanghae.concert_reservation.domain.payment.entity.Payment;
import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoEvent;
import com.hanghae.concert_reservation.domain.payment.event.dto.PaymentInfoPayload;
import com.hanghae.concert_reservation.domain.user.entity.UserPoint;
import com.hanghae.concert_reservation.infrastructure.jpa.concert.repository.ConcertJpaRepository;
import com.hanghae.concert_reservation.infrastructure.jpa.concert.repository.ConcertScheduleJpaRepository;
import com.hanghae.concert_reservation.infrastructure.jpa.concert.repository.ConcertSeatJpaRepository;
import com.hanghae.concert_reservation.infrastructure.jpa.concert.repository.ReservationJpaRepository;
import com.hanghae.concert_reservation.infrastructure.jpa.outbox.OutboxJpaRepository;
import com.hanghae.concert_reservation.infrastructure.jpa.payment.repository.PaymentJpaRepository;
import com.hanghae.concert_reservation.infrastructure.jpa.user.repository.UserPointJpaRepository;
import com.hanghae.concert_reservation.infrastructure.kafka.payment.PaymentProducer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class PaymentEventTest {

    @Autowired
    private UserPointJpaRepository userPointJpaRepository;

    @Autowired
    private ConcertJpaRepository concertJpaRepository;

    @Autowired
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    private ConcertSeatJpaRepository concertSeatJpaRepository;

    @Autowired
    private ReservationJpaRepository reservationJpaRepository;

    @Autowired
    private ConcertPaymentUseCase concertPaymentUseCase;

    @Autowired
    private OutboxJpaRepository outboxJpaRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentProducer paymentProducer;

    private static String receivedMessage;

    private final CountDownLatch latch = new CountDownLatch(1);

    @BeforeEach
    void setUp() {
        databaseCleanUp.execute();

        UserPoint userPoint = UserPoint.of(1L, BigDecimal.valueOf(100000));
        Concert concert = Concert.of("아이유콘서트");
        ConcertSchedule concertSchedule = ConcertSchedule.of(1L, LocalDateTime.now(), "콘서트홀");
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1, BigDecimal.valueOf(10000));
        Reservation reservation = Reservation.of(1L, 1L, concert.getName(), concertSchedule.getDate(), concertSeat.getPrice());

        userPointJpaRepository.save(userPoint);
        concertJpaRepository.save(concert);
        concertScheduleJpaRepository.save(concertSchedule);
        concertSeatJpaRepository.save(concertSeat);
        reservationJpaRepository.save(reservation);
    }

    @Test
    void testPaymentEvent() throws InterruptedException {
        // when
        PaymentCommand command = new PaymentCommand(1L, 1L);
        concertPaymentUseCase.payment(command);

        Thread.sleep(3000);

        // then
        int size = outboxJpaRepository.findAll().size();
        assertThat(size).isEqualTo(1);
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message) {
        receivedMessage = message;
        System.out.println("receivedMessage = " + receivedMessage);
        latch.countDown();
    }

    @KafkaListener(topics = "payment-external-info", groupId = "my-group")
    public void listen2(String message) {
        receivedMessage = message;
        System.out.println("receivedMessage = " + receivedMessage);
        latch.countDown();
    }

    @Test
    void testOutboxMessageProcessing() throws InterruptedException, JsonProcessingException {
        // given
        String eventKey = UUID.randomUUID().toString();
        String eventType = "PaymentExternalEvent";
        PaymentInfoEvent eventMessage = new PaymentInfoEvent(eventKey, eventType, 1L, "아이유콘서트", BigDecimal.valueOf(10000));

        // when
        paymentProducer.sendMessage("test-topic", eventMessage);

        // then
        boolean messageReceived = latch.await(5, TimeUnit.SECONDS);
        assertThat(messageReceived).isTrue();
        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage).contains(objectMapper.writeValueAsString(eventMessage));
    }

    @Test
    void 합쳐서테스트() throws InterruptedException, JsonProcessingException {
        // given
        String eventKey = UUID.randomUUID().toString();
        String eventType = "PaymentExternalEvent";
        PaymentInfoEvent eventMessage = new PaymentInfoEvent(eventKey, eventType, 1L, "아이유콘서트", BigDecimal.valueOf(10000));

        // when
        PaymentCommand command = new PaymentCommand(1L, 1L);
        concertPaymentUseCase.payment(command);

//        Thread.sleep(3000);

        // then
        int size = outboxJpaRepository.findAll().size();
        assertThat(size).isEqualTo(1);

        boolean messageReceived = latch.await(15, TimeUnit.SECONDS);
        assertThat(messageReceived).isTrue();
        assertThat(receivedMessage).isNotNull();
        PaymentInfoEvent receivedEvent = objectMapper.readValue(receivedMessage, PaymentInfoEvent.class);
        assertThat(receivedEvent.eventType()).isEqualTo(eventMessage.eventType());
        assertThat(receivedEvent.concertSeatId()).isEqualTo(eventMessage.concertSeatId());
        assertThat(receivedEvent.concertName()).isEqualTo(eventMessage.concertName());
        assertThat(receivedEvent.amount()).isEqualByComparingTo(eventMessage.amount());
    }
}
