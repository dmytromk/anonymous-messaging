package org.anonmes.messenger.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GoogleOAuthUser implements OAuth2User {
    private final String email;
    private final List<GrantedAuthority> authorities;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("email", email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return email;
    }
}
