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

    // TODO: implement getting all messages based on requester id
    @GetMapping
    public List<MessageResponseDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{receiver_id}")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long receiver_id) {
        return messageService.getAllMessagesByReceiver(receiver_id);
    }

    @GetMapping("/{receiver_id}/pages")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long receiver_id,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return messageService.getAllMessagesByReceiver(receiver_id, page, size);
    }

    @PostMapping("/{sender_id}")
    public MessageResponseDTO postMessage(@PathVariable Long sender_id, @RequestBody MessageCreateDTO createDTO) {
        return messageService.postMessage(sender_id, createDTO);
    }
}
