package org.anonmes.messenger.controller;

import lombok.RequiredArgsConstructor;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.dto.UserUpdateNameDTO;
import org.anonmes.messenger.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users") //or some other mapping
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserCreateDTO user) {
        return userService.save(user);
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
    public UserResponseDTO updateUser(@RequestBody UserUpdateNameDTO user) {
        return userService.update(user);
    }
}
