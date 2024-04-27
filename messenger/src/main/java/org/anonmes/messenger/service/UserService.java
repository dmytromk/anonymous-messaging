package org.anonmes.messenger.service;

import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.dto.UserUpdateNameDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAll();
    UserResponseDTO save(UserCreateDTO user);
    void delete(Long id);
    UserResponseDTO update(UserUpdateNameDTO user);
}
