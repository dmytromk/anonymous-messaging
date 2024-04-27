package org.anonmes.messenger.service;

import org.anonmes.messenger.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User save(User user);
}
