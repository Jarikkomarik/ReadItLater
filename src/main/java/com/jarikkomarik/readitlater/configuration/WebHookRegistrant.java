package com.jarikkomarik.readitlater.configuration;

import com.jarikkomarik.readitlater.model.TelegramHttpResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class WebHookRegistrant {


    @Value("${bot.token}")
    private String token;

    @Value("${bot.webhook.address}")
    private String webhookAddress;

    @Value("${bot.webhook.register.host}")
    private String registerHost;

    @Value("${bot.webhook.register.template}")
    private String registerTemplate;


    @PostConstruct
    private void registerWebHook() {
        WebClient client = WebClient.builder().baseUrl(registerHost).build();
        TelegramHttpResponse telegramHttpResponse = client
                .get()
                .uri(uriBuilder -> uriBuilder.path(registerTemplate).queryParam("url", webhookAddress).build(token))
                .retrieve()
                .bodyToMono(TelegramHttpResponse.class).block();

        if (telegramHttpResponse.ok()) {
            log.info("Successfully registered webhook to url: {}.", webhookAddress);
        } else {
            System.exit(1);
        }
    }
}
