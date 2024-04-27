package org.anonmes.messenger.service;

import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.MessageResponseDTO;

import java.util.List;

public interface MessageService {
    List<MessageResponseDTO> getAllMessages();
    List<MessageResponseDTO> getAllMessagesByReceiver(Long receiverId);
    List<MessageResponseDTO> getAllMessagesByReceiver(Long receiverId, int page, int size);
    MessageResponseDTO postMessage(Long senderId, MessageCreateDTO messageCreateDTO);

}
