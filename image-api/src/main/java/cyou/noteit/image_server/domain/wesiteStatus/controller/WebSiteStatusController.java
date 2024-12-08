package cyou.noteit.image_server.domain.wesiteStatus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSiteStatusController {
    @GetMapping("/")
    public ResponseEntity<Void> statusCheck() {
        return ResponseEntity.ok().build();
    }
}
