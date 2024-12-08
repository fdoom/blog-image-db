package cyou.noteit.image_server.global.scheduler;


import cyou.noteit.image_server.domain.wesiteStatus.service.WebsiteStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    @Value("${ALL_DOMAIN}")
    private List<String> ALL_DOMAIN;

    private final WebsiteStatusService websiteStatusService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void checkWebsiteStatus() {
        for (String domain : ALL_DOMAIN) {
            websiteStatusService.checkWebsiteStatus(domain);
        }
    }
}