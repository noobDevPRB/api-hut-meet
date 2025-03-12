package brp.meettime.api.client;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class HubSpotClient {

    private static final String CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";
    private static final String TOKEN_EXCHANGE_URL = "https://api.hubapi.com/oauth/v1/token";
    
    private final RestTemplate restTemplate;

    public HubSpotClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendCreateContactRequest(String requestBody, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(CREATE_CONTACT_URL, HttpMethod.POST, entity, String.class);
    }

    public String requestToken(Map<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.setAll(map);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        ResponseEntity<String> response = restTemplate.exchange(TOKEN_EXCHANGE_URL, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Erro ao obter token do HubSpot. Status: " + response.getStatusCode());
        }
    }
}
