package org.anonmes.messenger.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.model.UserCredentials;
import org.anonmes.messenger.service.UserCredentialsService;
import org.anonmes.messenger.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


@Configuration
@AllArgsConstructor
public class GoogleOAuth2AuthenticationSuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {
    private final UserCredentialsService userCredentialsService;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2User oauth2User = ((OAuth2AuthenticationToken)
                    authentication).getPrincipal();

            Optional<UserCredentials> userCredentials = userCredentialsService.loadUserByUsername(
                    Objects.requireNonNull(oauth2User.getAttribute("email")).toString()
            );

            // if user exists, generate token
            if (userCredentials.isPresent()) {
                String jwt = jwtUtil.generateToken(userCredentials.get().getEmail());
                jwt = "{ " + "\"jwt\": \"" + jwt + "\"}";
                response.getWriter().write(jwt);
                response.getWriter().flush();
            } else {
                // save user
                User user = new User();
                user.setEmail(Objects.requireNonNull(oauth2User.getAttribute("email")).toString());
                user.setCreatedAt(java.time.LocalDateTime.now());
                userCredentialsService.saveUser(user, "google");
                String jwt = jwtUtil.generateToken(user.getEmail());
                jwt = "{ " + "\"jwt\": \"" + jwt + "\"}";
                response.getWriter().write(jwt);
                response.getWriter().flush();
            }
        }
    }
}
