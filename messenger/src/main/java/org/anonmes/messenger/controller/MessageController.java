package org.anonmes.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@CrossOrigin(
        origins = "http://localhost:3000",
        methods = {RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.POST},
        maxAge=3600
)
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
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
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
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found or page not found",
                    content = @Content)
    })
    @GetMapping("/{receiver_id}/pages")
    public List<MessageResponseDTO> getAllMessagesByReceiver(@PathVariable Long receiver_id,
                                                             @RequestParam Integer page,
                                                             @RequestParam Integer size) {
        return messageService.getAllMessagesByReceiver(receiver_id, page, size);
    }

    @Operation(
            summary = "Send message",
            description = "Send message from sender_id user to toId user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User(s) not found",
                    content = @Content)
    })
    @PostMapping("/{sender_id}")
    public MessageResponseDTO postMessage(@PathVariable
                                          @Parameter(description = "Id of sending user") Long sender_id,
                                          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object containing receiver's id and message content")
                                          @RequestBody MessageCreateDTO createDTO) {
        return messageService.postMessage(sender_id, createDTO);
    }
}
