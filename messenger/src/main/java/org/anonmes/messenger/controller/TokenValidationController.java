package org.anonmes.messenger.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT},
        maxAge=3600

)
public class TokenValidationController {

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Value("${KEYCLOAK_SECRET}")
    private String keyCloakSecret;

    @Value("${KEYCLOAK_CLIENT_ID}")
    private String keyCloakClientId;

    @GetMapping("/validate")
    public Map<String, Object> validateToken(@RequestParam String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            Map<String, Object> payload = (Map<String, Object>) claims.get("payload");
            String provider = (String) payload.get("provider");
            String accessToken = (String) payload.get("access_token");
            // String refreshToken = (String) payload.get("refresh_token");

            if (!isTokenValid(provider, accessToken)) {
               // if (refreshToken != null && !refreshToken.isEmpty()) {
               //     accessToken = refreshToken(accessToken, refreshToken, provider);
               // }
                return Map.of("status", "Token is invalid");
            }

            return Map.of("status", "Token is valid", "decoded", claims);
        } catch (SignatureException ex) {
            return Map.of("status", "Token is invalid due to signature error");
        } catch (Exception ex) {
            return Map.of("status", "Token is invalid due to error: " + ex.getMessage());
        }
    }

    private boolean isTokenValid(String provider, String accessToken) {
        if (provider.equals("google")) {
            String result = sendGoogleValidationRequest("https://www.googleapis.com/oauth2/v1/tokeninfo?access_token="+accessToken);
            return !(result.contains("\"error\": \"invalid_token\""));
        } else if (provider.equals("keycloak")) {
            String result = sendKeycloakValidationRequest(accessToken);
            return !result.equals("error");
        }
        return false;
    }

    private String sendKeycloakValidationRequest(String accessToken) {
        String clientId = keyCloakClientId;
        String clientSecret = keyCloakSecret;
        String introspectionUrl = "http://localhost:8888/realms/master/protocol/openid-connect/token/introspect";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", accessToken);
        map.add("token_type_hint", "access_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(introspectionUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send validation request: " + e.getMessage(), e);
        }
    }

    private String sendGoogleValidationRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}