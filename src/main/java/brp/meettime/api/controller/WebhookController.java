package brp.meettime.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brp.meettime.api.model.WebhookEventDTO;
import webhook.WebhookHandler;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {

    private final WebhookHandler webhookHandler;

    public WebhookController(WebhookHandler webhookHandler) {
        this.webhookHandler = webhookHandler;
    }

    @PostMapping("/contact-created")
    public ResponseEntity<Void> handleContactCreation(@RequestBody List<WebhookEventDTO> webhookEventDTOList) {
        if (webhookEventDTOList != null && !webhookEventDTOList.isEmpty()) {
            webhookEventDTOList.forEach(webhookHandler::processContactCreation);
        }
        return ResponseEntity.ok().build();
    }
}
