package org.anonmes.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.MessageResponseDTO;
import org.anonmes.messenger.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages/")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    public List<MessageResponseDTO> getAllMessages() {
        return messageService.getAllMessages();
    }
    @GetMapping("/{id}")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long id) {
        return messageService.getAllMessagesByReceiver(id);
    }
    @GetMapping("/{id}/pages")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long id,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return messageService.getAllMessagesByReceiver(id, page, size);
    }

    @PostMapping("/{id}")
    public MessageResponseDTO postMessage(@PathVariable Long id, @RequestBody MessageCreateDTO createDTO) {
        return messageService.postMessage(id, createDTO);
    }
}
