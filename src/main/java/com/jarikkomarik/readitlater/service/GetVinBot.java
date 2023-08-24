package com.jarikkomarik.readitlater.service;

import com.jarikkomarik.readitlater.model.SpzApiResponse;
import com.jarikkomarik.readitlater.model.TelegramHttpResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.util.Map;

@Component
public class GetVinBot extends SpringWebhookBot {

    private String apiUrl = "https://www.srovnejto.cz";
    private String apiPath = "/PovWisardStart/GetCarDataBySpz/";


    public GetVinBot(SetWebhook setWebhook, String botToken) {
        super(setWebhook, botToken);
    }

    @SneakyThrows
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        WebClient.builder()
                .build()
                .get()
                .uri("https://www.srovnejto.cz/PovWisardStart/GetCarDataBySpz/?spz={spz}}", update.getMessage().getText().trim())
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(s -> {
                    s = s.split("Vin")[1].split(",")[0].replaceAll("\"|:", "");
                    String message = s.equals("null") ? "Failed to get vin from spz: " + update.getMessage().getText() : s;
                    try {
                        execute(SendMessage.builder()
                                .chatId(update.getMessage().getChatId())
                                .text(message)
                                .build());
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                });
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }

    @Override
    public String getBotUsername() {
        return null;
    }
}
