package org.anonmes.messenger.service;

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
        User user1 = new User();
        User user2 = new User();
        userService.save(user1);
        userService.save(user2);
        assertTrue(userService.getAll().containsAll(List.of(user1, user2)));
    }

    @Test
    void save() {
        User user = new User();
        String name = "John";
        user.setName(name);
        User savedUser = userService.save(user);
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedAt());
        assertEquals(name, savedUser.getName());
    }
}