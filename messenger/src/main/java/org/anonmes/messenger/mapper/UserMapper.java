package org.anonmes.messenger.mapper;

import org.anonmes.messenger.config.MapperConfig;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDTO toResponse(User model);
    User fromCreate(UserCreateDTO createDTO);
    @Named("userById")
    default User getUserById(Long id) {
        return id == null ? null : new User(id);
    }
    @Named("userToId")
    default Long getUserId(User user) {
        return user == null ? null : user.getId();
    }
}
