package com.jarikkomarik.readitlater.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class ConfigBeans {
    @Value("${bot.webhook.address}")
    private String webhookAddress;

    @Value("${bot.token}")
    private String botToken;

    @Bean
    public SetWebhook getSetWebhook() {
        return SetWebhook.builder().url(webhookAddress).build();
    }

    @Bean
    public String getBotToken() {
        return botToken;
    }
}
