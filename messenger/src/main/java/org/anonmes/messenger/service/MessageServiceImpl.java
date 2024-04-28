package org.anonmes.messenger.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.MessageResponseDTO;
import org.anonmes.messenger.mapper.MessageMapper;
import org.anonmes.messenger.model.Message;
import org.anonmes.messenger.model.User;
import org.anonmes.messenger.repository.MessageRepository;
import org.anonmes.messenger.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final MessageMapper mapper;
    private final UserRepository userRepository;
    @Override
    public List<MessageResponseDTO> getAllMessages() {
        return repository.findAll()
                .stream().map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<MessageResponseDTO> getAllMessagesByReceiver(Long receiverId) {
        return repository.findAllByToId(receiverId)
                .stream().map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<MessageResponseDTO> getAllMessagesByReceiver(Long receiverId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAllByToId(receiverId, pageable)
                .stream().map(mapper::toResponse)
                .toList();
    }

    @Override
    public MessageResponseDTO postMessage(Long senderId, MessageCreateDTO messageCreateDTO) {
        Message message = new Message();
        message.setContent(messageCreateDTO.getContent());
        if (senderId != null) {
            Optional<User> sender = userRepository.findById(senderId);
            message.setFrom(sender.orElseThrow(() -> new EntityNotFoundException("No user with id " + senderId)));
        }
        Long toId = message.getTo().getId();
        Optional<User> receiver = userRepository.findUserByEmail(messageCreateDTO.getToEmail());
        message.setTo(receiver.orElseThrow(() -> new EntityNotFoundException("No user with email " + toId)));
        message.setCreatedAt(LocalDateTime.now());
        Message saved = repository.save(message);
        return mapper.toResponse(saved);
    }
}
