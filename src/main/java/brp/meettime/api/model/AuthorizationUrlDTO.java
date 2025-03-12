package brp.meettime.api.model;

public class AuthorizationUrlDTO {
    private String authorizationUrl;

    public AuthorizationUrlDTO(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }
    
}