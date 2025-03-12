package brp.meettime.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import brp.meettime.api.client.HubSpotClient;
import brp.meettime.api.model.ContactDTO;
import util.ValidateUtil;

@Service
public class ContactService {

	private final HubSpotClient hubSpotClient;
    private final ObjectMapper objectMapper;

    public ContactService(HubSpotClient hubSpotClient, ObjectMapper objectMapper) {
        this.hubSpotClient = hubSpotClient;
        this.objectMapper = objectMapper;
    }

    public ContactDTO createContact(ContactDTO contactDTO, String accessToken) {
        try {
            Map<String, Object> properties = new HashMap<>();
            properties.put("email", contactDTO.getEmail());
            properties.put("firstname", contactDTO.getFirstname());
            properties.put("lastname", contactDTO.getLastname());
            
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("properties", properties);
            
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);
            ResponseEntity<String> response = hubSpotClient.sendCreateContactRequest(requestBody, accessToken);
            
            ValidateUtil.rateLimitCheck(response.getHeaders());
            
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return contactDTO;
            } else {
                throw new RuntimeException("Erro ao criar o contato: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar JSON da requisição: " + e.getMessage(), e);
        }
    }
}
