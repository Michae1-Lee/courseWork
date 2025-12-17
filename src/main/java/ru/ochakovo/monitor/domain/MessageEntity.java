package ru.ochakovo.monitor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "messages")
@Setter
@Getter
public class MessageEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String externalId; // messageId в Telegram (chatId:messageId удобно)

    @Column(nullable = false)
    private Long chatId;

    @Column(nullable = false)
    private Long telegramUserId;

    @Column(nullable = false)
    private Instant sentAt;

    @Column(columnDefinition = "text")
    private String text;

    private String replyToExternalId; // chatId:messageId исходного сообщения (если reply)

    private Integer responseTimeSec; // если reply, то (sentAt - original.sentAt) в секундах

    // getters/setters ...
}
