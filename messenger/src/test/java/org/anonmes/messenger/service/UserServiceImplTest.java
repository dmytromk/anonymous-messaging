package org.anonmes.messenger.service;

import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UserServiceImplTest {
    private final UserService userService;
    @Autowired
    UserServiceImplTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void getAll() {
        UserCreateDTO user1 = new UserCreateDTO();
        UserCreateDTO user2 = new UserCreateDTO();
        UserResponseDTO saved1 = userService.save(user1);
        UserResponseDTO saved2 = userService.save(user2);
        List<UserResponseDTO> all = userService.getAll();
        assertTrue(all.containsAll(List.of(saved1, saved2)));
    }

    @Test
    void save() {
        UserCreateDTO user = new UserCreateDTO();
        String name = "John";
        user.setName(name);
        UserResponseDTO savedUser = userService.save(user);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedAt());
        assertEquals(name, savedUser.getName());
    }
}