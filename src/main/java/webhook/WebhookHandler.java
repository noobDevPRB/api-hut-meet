package webhook;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import brp.meettime.api.model.WebhookEventDTO;

@Component
public class WebhookHandler  {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public void processContactCreation(WebhookEventDTO event) {
	    try {
	        String jsonEvent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
	        System.out.println("Evento processado: " + jsonEvent);
		} catch (JsonProcessingException e) {
		    System.err.println("Erro ao converter evento para JSON: " + e.getMessage());
		}
	}
	
}
