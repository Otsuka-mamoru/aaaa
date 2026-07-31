package jp.levtech.rookie.tutorial.controller;

import jp.levtech.rookie.tutorial.service.DiscordNotification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final DiscordNotification discordService;

    public TestController(DiscordNotification discordService) {
        this.discordService = discordService;
    }

    @GetMapping("/test/discord")
    public String testDiscord() {
        discordService.send("テスト通知です！");
        return "Discord通知を送信しました";
    }
}