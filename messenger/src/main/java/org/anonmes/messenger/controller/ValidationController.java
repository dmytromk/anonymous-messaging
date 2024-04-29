package org.anonmes.messenger.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.anonmes.messenger.validator.AccessTokenValidator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT},
        maxAge=3600

)
public class ValidationController {

    private final AccessTokenValidator validator;

    public ValidationController(AccessTokenValidator validator) {
        this.validator = validator;
    }
    @GetMapping("/validate-token")
    public String validateToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("next-auth.session-token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }

        if (accessToken == null) {
            return "No access token found";
        }

        return validator.validateAccessTokenWithGoogle(accessToken);
    }

}
