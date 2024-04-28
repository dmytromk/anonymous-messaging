package org.anonmes.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.MessageResponseDTO;
import org.anonmes.messenger.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages/")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "Messages API")
public class MessageController {
    private final MessageService messageService;

    @Operation(
            summary = "Get all messages",
            description = "Get all messages sent in the messenger.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))}),
    })
    @GetMapping
    public List<MessageResponseDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @Operation(
            summary = "Get all messages by receiver",
            description = "Get all messages that are sent to receiver (not sent by them) with given id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))})
    })
    @GetMapping("/{receiver_id}")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long receiver_id) {
        return messageService.getAllMessagesByReceiver(receiver_id);
    }

    @Operation(
            summary = "Get all messages by receiver in range",
            description = "Get all messages in range (set by page and size parameters) " +
                    "that are sent to receiver (not sent by them) with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "User not found or page not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageResponseDTO.class)))})
    })
    @GetMapping("/{receiver_id}/pages")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long receiver_id,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return messageService.getAllMessagesByReceiver(receiver_id, page, size);
    }

    @Operation(
            summary = "Send message",
            description = "Send message from user with id of sender_id. Receiver's id and content are set in request body respectively.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User(s) not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))})
    })
    @PostMapping("/{sender_id}")
    public MessageResponseDTO postMessage(@PathVariable Long sender_id,
                                          @RequestBody MessageCreateDTO createDTO) {
        return messageService.postMessage(sender_id, createDTO);
    }
}
