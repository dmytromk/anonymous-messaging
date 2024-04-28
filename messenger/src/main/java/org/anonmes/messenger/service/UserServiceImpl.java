package org.anonmes.messenger.service;

import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.dto.UserUpdateNameDTO;
import org.anonmes.messenger.exception.UserNotFoundException;
import org.anonmes.messenger.mapper.UserMapper;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    private final UserMapper userMapper;
    @Override
    public List<UserResponseDTO> getAll() {
        return repository.findAll()
                .stream().map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDTO save(UserCreateDTO createDTO) {
        User user = userMapper.fromCreate(createDTO);
        user.setCreatedAt(LocalDateTime.now());
        User save = repository.save(user);
        return userMapper.toResponse(save);
    }

    @Override
    public void delete(Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()) {
            repository.deleteById(userId);
        }
        throw new UserNotFoundException("Cannot find user by id: " + userId);
    }

    @Override
    public UserResponseDTO update(UserUpdateNameDTO updateUser) {
        Optional<User> userOptional = repository.findById(updateUser.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(updateUser.getName());
            User updated = repository.save(user);
            return userMapper.toResponse(updated);
        }
        throw new UserNotFoundException("Cannot find user by id: " + updateUser.getId());
    }
}
