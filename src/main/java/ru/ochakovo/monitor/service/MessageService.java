package ru.ochakovo.monitor.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ochakovo.monitor.domain.MessageEntity;
import ru.ochakovo.monitor.repo.MessageRepository;

import java.time.Instant;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository repo;

    public MessageService(MessageRepository repo) { this.repo = repo; }

    public MessageEntity saveIncomingTextMessage(Message msg) {
        MessageEntity e = new MessageEntity();
        String externalId = msg.getChatId() + ":" + msg.getMessageId();

        e.setExternalId(externalId);
        e.setChatId(msg.getChatId());
        e.setTelegramUserId(msg.getFrom().getId());
        e.setSentAt(Instant.ofEpochSecond(msg.getDate()));
        e.setText(msg.getText());

        // reply support
        if (msg.getReplyToMessage() != null) {
            String replyTo = msg.getChatId() + ":" + msg.getReplyToMessage().getMessageId();
            e.setReplyToExternalId(replyTo);

            Optional<MessageEntity> orig = repo.findByExternalId(replyTo);
            orig.ifPresent(o -> {
                long sec = e.getSentAt().getEpochSecond() - o.getSentAt().getEpochSecond();
                if (sec >= 0) e.setResponseTimeSec((int) sec);
            });
        }

        return repo.save(e);
    }
}
