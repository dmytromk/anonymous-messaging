package org.anonmes.messenger.security;

import lombok.AllArgsConstructor;
import org.anonmes.messenger.model.UserCredentials;
import org.anonmes.messenger.service.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserCredentialsService userCredentialsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> ouser = userCredentialsService.loadUserByUsername(username);
        if (ouser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserCredentials user = ouser.get();
        return new UserDetailsImpl(user.getEmail(), user.getPassword(), user.isActive(), user.getRole(), user.getProvider());
    }
}
