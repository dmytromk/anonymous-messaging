package org.anonmes.messenger.service;

import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.mapper.UserMapper;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        repository.deleteById(userId);
    }
}
