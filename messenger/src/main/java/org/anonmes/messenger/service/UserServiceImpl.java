package org.anonmes.messenger.service;

import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User save(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return repository.save(user);
    }
}
