package ru.ochakovo.monitor.service;

import org.springframework.stereotype.Service;
import ru.ochakovo.monitor.domain.Incident;
import ru.ochakovo.monitor.domain.MessageEntity;
import ru.ochakovo.monitor.repo.IncidentRepository;

import java.time.Instant;

@Service
public class IncidentService {

    private final IncidentRepository repo;

    public IncidentService(IncidentRepository repo) {
        this.repo = repo;
    }

    public Incident createKeywordIncident(MessageEntity msg, String keyword) {
        Incident i = new Incident();
        i.setType("KEYWORD");
        i.setSeverity("HIGH");
        i.setChatId(msg.getChatId());
        i.setTelegramUserId(msg.getTelegramUserId());
        i.setDetectedAt(Instant.now());
        i.setStatus("NEW");
        i.setDescription("Найдено ключевое слово: " + keyword + " (msg=" + msg.getExternalId() + ")");
        return repo.save(i);
    }
}
