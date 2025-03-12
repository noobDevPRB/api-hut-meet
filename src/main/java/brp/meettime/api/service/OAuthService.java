package brp.meettime.api.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import brp.meettime.api.client.HubSpotClient;
import brp.meettime.api.config.OAuthConfig;

@Service
public class OAuthService {

    private static final String AUTHORIZATION_URL_TEMPLATE = "https://app.hubspot.com/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s";
    private final OAuthConfig oAuthConfig;
    private final HubSpotClient hubSpotClient;

    public OAuthService(OAuthConfig oAuthConfig, HubSpotClient hubSpotClient) {
        this.oAuthConfig = oAuthConfig;
        this.hubSpotClient = hubSpotClient;
    }

    public String generateAuthorizationUrl() {
        return String.format(AUTHORIZATION_URL_TEMPLATE, oAuthConfig.getClientId(), oAuthConfig.getRedirectUri(), oAuthConfig.getScope());
    }

    public String exchangeCodeForAccessToken(String code) {
        return hubSpotClient.requestToken(createTokenRequestBody(code, "authorization_code"));
    }

    private Map<String, String> createTokenRequestBody(String token, String grantType) {
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", grantType);
        body.put("client_id", oAuthConfig.getClientId());
        body.put("client_secret", oAuthConfig.getClientSecret());
        body.put("redirect_uri", oAuthConfig.getRedirectUri());
        body.put(grantType.equals("authorization_code") ? "code" : "refresh_token", token);
        return body;
    }
}
