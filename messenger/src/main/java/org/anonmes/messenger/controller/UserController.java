package org.anonmes.messenger.controller;

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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO user) {
        UserResponseDTO save = userService.save(user);
        URI location = getUri(save);
        return ResponseEntity.created(location).body(save);
    }

    private static URI getUri(UserResponseDTO save) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAll();
    }

    // TODO (optional): cases when the user deletion is unsuccessful
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateNameDTO user) {
        return ResponseEntity.ok(userService.update(user));
    }
}
