package org.anonmes.messenger.mapper;

import org.anonmes.messenger.config.MapperConfig;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDTO toResponse(User model);
    User fromCreate(UserCreateDTO createDTO);
}
