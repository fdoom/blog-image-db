package cyou.noteit.image_server.domain.wesiteStatus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebsiteStatusService {

    @Value("${DISCORD_URL}")
    private String DISCORD_URL;

    private final RestTemplate restTemplate = new RestTemplate();

    private final static String TITLE = "WEB STATUS CHECKER";

    @Async
    public void checkWebsiteStatus(String domain) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    domain,
                    HttpMethod.HEAD,
                    null,
                    Void.class
            );

            sendDiscordNotification("✅ " + domain + ": " + response.getStatusCode(),
                    0x00FF00); // 초록색

        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().is4xxClientError()) {
                sendDiscordNotification("⚠️ " + domain + ": " + e.getStatusCode(),
                        0xFFFF00); // 노란색
            } else {
                sendDiscordNotification("❌ " + domain + ": " +  e.getStatusCode(),
                        0xFF0000); // 빨간색
            }
        }
    }

    private void sendDiscordNotification(String description, int color) {
        try {
            Map<String, Object> embed = new HashMap<>();
            embed.put("title", TITLE);
            embed.put("description", description);
            embed.put("color", color);

            Map<String, Object> payload = new HashMap<>();
            payload.put("embeds", new Map[]{embed});

            restTemplate.postForEntity(DISCORD_URL, payload, String.class);
        } catch (Exception e) {
            System.err.println("Failed to send Discord notification: " + e.getMessage());
        }
    }
}