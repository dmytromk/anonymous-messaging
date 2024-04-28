package org.anonmes.messenger.service;

import lombok.AllArgsConstructor;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.model.UserCredentials;
import org.anonmes.messenger.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialsService {
    @Autowired
    UserCredentialsRepository userCredentialsRepository;

    public Optional<UserCredentials> loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialsRepository.findByEmail(username);
    }

    public UserCredentials save(UserCredentials userCredentials) {
        return userCredentialsRepository.save(userCredentials);
    }

    public void saveUser(User user, String provider) {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(user.getEmail());
        userCredentials.setPassword(null);
        userCredentials.setRole("USER");
        userCredentials.setActive(true);
        userCredentials.setProvider(provider);
        userCredentials.addUser(user);
        userCredentialsRepository.save(userCredentials);
    }
}
