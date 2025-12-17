package ru.ochakovo.monitor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
        name = "activity_metrics",
        indexes = {
                @Index(name="idx_metrics_chat_user_period", columnList="chatId,telegramUserId,periodStart,periodEnd")
        }
)
@Getter
@Setter
public class ActivityMetric {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private Long chatId;
    @Column(nullable = false) private Long telegramUserId;

    @Column(nullable = false) private Instant periodStart;
    @Column(nullable = false) private Instant periodEnd;

    @Column(nullable = false) private long messagesCount;
    @Column(nullable = false) private long repliesCount;

    // среднее время ответа в секундах (если есть replies)
    private Long avgResponseTimeSec;

    private Instant lastMessageAt;

    // getters/setters
}
