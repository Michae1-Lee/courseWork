package ru.ochakovo.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "telegram.webhook")
public record TelegramWebhookProperties(String path, String publicUrl) {}
