package ru.ochakovo.monitor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "incidents")
@Setter
@Getter
public class Incident {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type; // KEYWORD, NO_RESPONSE etc.

    @Column(nullable = false)
    private String severity; // LOW/MEDIUM/HIGH

    @Column(nullable = false)
    private Long chatId;

    private Long telegramUserId;

    @Column(nullable = false)
    private Instant detectedAt;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String status; // NEW/ACK/RESOLVED

    // getters/setters ...
}
