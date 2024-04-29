package org.anonmes.messenger.validator;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccessTokenValidator {

    public String validateAccessTokenWithGoogle(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=" + accessToken;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return "Token is valid: " + response.getBody();
        } catch (Exception e) {
            return "Token validation error: " + e.getMessage();
        }
    }
}
