package ru.ochakovo.monitor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ochakovo.monitor.domain.MessageEntity;

import java.util.List;

@Service
public class RuleEngineService {

    private final List<String> keywords;
    private final IncidentService incidentService;

    public RuleEngineService(
            @Value("${monitoring.keywords}") List<String> keywords,
            IncidentService incidentService
    ) {
        this.keywords = keywords;
        this.incidentService = incidentService;
    }

    public void checkRules(MessageEntity msg) {
        String text = msg.getText() == null ? "" : msg.getText().toLowerCase();

        for (String kw : keywords) {
            if (text.contains(kw.toLowerCase())) {
                incidentService.createKeywordIncident(msg, kw);
                break;
            }
        }
    }
}
