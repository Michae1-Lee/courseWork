package ru.ochakovo.monitor.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.ochakovo.monitor.domain.ActivityMetric;
import ru.ochakovo.monitor.domain.MessageEntity;
import ru.ochakovo.monitor.repo.ActivityMetricRepository;
import ru.ochakovo.monitor.repo.MessageRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MetricService {

    private final MessageRepository messageRepo;
    private final ActivityMetricRepository metricsRepo;

    public MetricService(MessageRepository messageRepo, ActivityMetricRepository metricsRepo) {
        this.messageRepo = messageRepo;
        this.metricsRepo = metricsRepo;
    }

    // каждые 5 минут считаем метрики за последний час
    @Scheduled(fixedDelayString = "PT5M")
    public void calculateLastHourMetrics() {
        Instant to = Instant.now();
        Instant from = to.minus(1, ChronoUnit.HOURS);

        List<MessageEntity> msgs = messageRepo.findAllInPeriod(from, to);

        record Key(Long chatId, Long userId) {}
        Map<Key, List<MessageEntity>> grouped = new HashMap<>();
        for (var m : msgs) grouped.computeIfAbsent(new Key(m.getChatId(), m.getTelegramUserId()), k -> new ArrayList<>()).add(m);

        for (var entry : grouped.entrySet()) {
            var key = entry.getKey();
            var list = entry.getValue();

            long messagesCount = list.size();
            long repliesCount = list.stream().filter(m -> m.getReplyToExternalId() != null).count();

            var responseTimes = list.stream()
                    .map(MessageEntity::getResponseTimeSec)
                    .filter(Objects::nonNull)
                    .mapToLong(Integer::longValue)
                    .toArray();

            Long avg = responseTimes.length == 0 ? null :
                    Math.round(Arrays.stream(responseTimes).average().orElse(0));

            Instant last = list.stream().map(MessageEntity::getSentAt).max(Comparator.naturalOrder()).orElse(null);

            ActivityMetric metric = new ActivityMetric();
            metric.setChatId(key.chatId());
            metric.setTelegramUserId(key.userId());
            metric.setPeriodStart(from);
            metric.setPeriodEnd(to);
            metric.setMessagesCount(messagesCount);
            metric.setRepliesCount(repliesCount);
            metric.setAvgResponseTimeSec(avg);
            metric.setLastMessageAt(last);

            metricsRepo.save(metric);
        }
    }
}
