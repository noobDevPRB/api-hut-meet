package brp.meettime.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import webhook.WebhookHandler;

@Configuration
public class AppConfig {
    
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public WebhookHandler webhookHandler() {
        return new WebhookHandler();
    }
}
