package ru.ochakovo.monitor.bot;

import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("${telegram.webhook.path}")
public class TelegramWebhookController {

    private final TelegramUpdateHandler handler;

    public TelegramWebhookController(TelegramUpdateHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    public void onUpdate(@RequestBody Update update) {
        handler.handle(update);
    }
}
