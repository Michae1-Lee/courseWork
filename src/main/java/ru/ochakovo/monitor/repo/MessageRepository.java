package ru.ochakovo.monitor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ochakovo.monitor.domain.MessageEntity;

import java.time.Instant;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    long countByChatIdAndTelegramUserIdAndSentAtBetween(Long chatId, Long telegramUserId, Instant from, Instant to);
    Optional<MessageEntity> findByExternalId(String externalId);

    @Query("""
  select m from MessageEntity m
  where m.sentAt between :from and :to
""")
    java.util.List<MessageEntity> findAllInPeriod(@Param("from") Instant from, @Param("to") Instant to);
}
