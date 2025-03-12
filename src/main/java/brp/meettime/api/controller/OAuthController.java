package brp.meettime.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import brp.meettime.api.service.OAuthService;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }
    
    @GetMapping("/authorize")
    public String getAuthorizationUrl() {
        return oAuthService.generateAuthorizationUrl();
    }
    
    @GetMapping("/callback")
    public ResponseEntity<String> handleOAuthCallback(@RequestParam(value = "code", required = false) String code) {
        System.out.println("Código recebido: " + code);

        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro: Código de autorização não encontrado.");
        }

        try {
            String tokenResponse = oAuthService.exchangeCodeForAccessToken(code);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(tokenResponse);
            String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            System.out.println("Evento processado" + formattedJson);

            return ResponseEntity.ok(formattedJson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Falha ao processar callback OAuth\"}");
        }
    }
}


