package org.anonmes.messenger.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.dto.UserUpdateNameDTO;
import org.anonmes.messenger.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users API")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Create a new user, given their name, email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "invalid parameters",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO user) {
        UserResponseDTO saved = userService.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @Operation(
            summary = "Get all users",
            description = "Get list of all of the users of the messenger.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))})
    })
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAll();
    }

    // TODO (optional): cases when the user deletion is unsuccessful
    @Operation(
            summary = "Delete user",
            description = "Delete user given their id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "user not found")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(
            summary = "Update user's name",
            description = "Update name of the user, given their id and new username set in request body respectively.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateNameDTO user) {
        return ResponseEntity.ok(userService.update(user));
    }
}
