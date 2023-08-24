package com.jarikkomarik.readitlater.controller;

import com.jarikkomarik.readitlater.service.GetVinBot;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public record DemoController(GetVinBot getVinBot) {

    @SneakyThrows
    @GetMapping("/test")
    public String onUpdateReceived() {
        return "test";
    }
}
