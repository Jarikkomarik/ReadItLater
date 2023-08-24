package com.jarikkomarik.readitlater.controller;

import com.jarikkomarik.readitlater.service.ReadItLaterBot;
import com.jarikkomarik.readitlater.service.UpdateRoutingService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public record TelegramUpdateController(ReadItLaterBot readItLaterBot, UpdateRoutingService updateRoutingService) {

    @SneakyThrows
    @PostMapping("/telegram-api")
    public void onUpdateReceived(@RequestBody Update update) {
        updateRoutingService.processUpdate(update);
    }
}
