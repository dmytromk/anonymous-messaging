package org.anonmes.messenger.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.anonmes.messenger.model.UserLogin;
import org.anonmes.messenger.service.UserLoginService;
import org.anonmes.messenger.util.JwtUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;


@Configuration
@AllArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler extends
        SimpleUrlAuthenticationSuccessHandler {
    private final UserLoginService userLoginService;
    private final  JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2User oauth2User = ((OAuth2AuthenticationToken)
                    authentication).getPrincipal();

            // check if user exists
            try {
                // if user exists, generate token
                UserDetails userDetails = userLoginService.loadUserByUsername(
                        Objects.requireNonNull(oauth2User.getAttribute("email")).toString());
                String jwt = jwtUtil.generateToken(userDetails.getUsername());
                response.getWriter().write(jwt);
                response.getWriter().flush();
            } catch (Exception e) {
                // save user
                UserLogin user = new UserLogin(Objects.requireNonNull(oauth2User.getAttribute("email")).toString());
                userLoginService.saveUser(user);
                String jwt = jwtUtil.generateToken(user.getUsername());
                response.getWriter().write(jwt);
                response.getWriter().flush();
            }
        }
    }
}
