package org.anonmes.messenger.service;

import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import static org.anonmes.messenger.util.FunctionalUtils.*;
@Service
public class Insertions {
    public List<UserCreateDTO> generateUsers(Collection<String> names) {
        return names.stream()
                .map(name -> {
                    UserCreateDTO userCreateDTO = new UserCreateDTO();
                    userCreateDTO.setName(name);
                    userCreateDTO.setEmail(name+"@mail.com");
                    return userCreateDTO;
                }
                ).toList();
    }
    public <T, E> List<E> saveAll(List<T> models, Function<T, E> saver) {
        return models.stream().map(saver).toList();
    }

    public MessageCreateDTO generateMessage(String to, String content) {
        MessageCreateDTO messageCreateDTO = new MessageCreateDTO();
        messageCreateDTO.setContent(content);
        messageCreateDTO.setToEmail(to);
        return messageCreateDTO;
    }

    public List<MessageCreateDTO> generateMessages(String to, String base, int amount) {
        return range(amount)
                .map(i -> generateMessage(to, base + i))
                .toList();
    }
}
