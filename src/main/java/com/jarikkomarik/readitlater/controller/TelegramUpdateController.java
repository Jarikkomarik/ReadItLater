package com.jarikkomarik.readitlater.controller;

import com.jarikkomarik.readitlater.service.GetVinBot;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public record TelegramUpdateController(GetVinBot getVinBot) {

    @SneakyThrows
    @PostMapping("/telegram-api")
    public void onUpdateReceived(@RequestBody Update update) {
        getVinBot.onWebhookUpdateReceived(update);
    }
}
