package jp.levtech.rookie.tutorial.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DiscordNotification{

    private final WebClient webClient = WebClient.create();

    @Value("${discord.webhook.url}")
    private String webhookUrl;

    public void send(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("content", message);

        webClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}