package ru.ochakovo.monitor.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.ochakovo.monitor.config.TelegramBotProperties;
import ru.ochakovo.monitor.config.TelegramWebhookProperties;

@Component
@ConditionalOnProperty(prefix = "telegram.webhook", name = "public-url")
public class WebhookRegistrar {

    private final TelegramBotProperties bot;
    private final TelegramWebhookProperties webhook;

    public WebhookRegistrar(TelegramBotProperties bot, TelegramWebhookProperties webhook) {
        this.bot = bot;
        this.webhook = webhook;
    }

    @PostConstruct
    public void register() throws Exception {
        if (webhook.publicUrl().isBlank()) return;

        new TelegramLongPollingBot(bot.token()) {
            @Override public String getBotUsername() { return bot.username(); }
            @Override public void onUpdateReceived(org.telegram.telegrambots.meta.api.objects.Update u) {}
        }.execute(SetWebhook.builder()
                .url(webhook.publicUrl() + webhook.path())
                .build());
    }
}
