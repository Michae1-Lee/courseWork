package ru.ochakovo.monitor.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ochakovo.monitor.service.MessageService;
import ru.ochakovo.monitor.service.RuleEngineService;

@Component
public class TelegramUpdateHandler {

    private final MessageService messageService;
    private final RuleEngineService ruleEngine;

    public TelegramUpdateHandler(MessageService messageService, RuleEngineService ruleEngine) {
        this.messageService = messageService;
        this.ruleEngine = ruleEngine;
    }

    public void handle(Update update) {
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) return;

        var msg = update.getMessage();
        var saved = messageService.saveIncomingTextMessage(msg);

        ruleEngine.checkRules(saved);
    }
}
