package org.anonmes.messenger.mapper;

import org.anonmes.messenger.config.MapperConfig;
import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.MessageResponseDTO;
import org.anonmes.messenger.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {UserMapper.class})
public interface MessageMapper {

    @Mapping(source = "to", target = "toId", qualifiedByName = "userToId")
    MessageResponseDTO toResponse(Message message);
}
