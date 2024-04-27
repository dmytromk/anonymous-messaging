package org.anonmes.messenger.service;

import org.anonmes.messenger.dto.MessageCreateDTO;
import org.anonmes.messenger.dto.MessageResponseDTO;
import org.anonmes.messenger.dto.UserCreateDTO;
import org.anonmes.messenger.dto.UserResponseDTO;
import org.anonmes.messenger.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class MessageServiceImplTest {

    private final MessageService messageService;
    private final UserService userService;
    private final Insertions insertions;
    @Autowired
    MessageServiceImplTest(MessageService messageService, UserService userService, Insertions insertions) {
        this.messageService = messageService;
        this.userService = userService;
        this.insertions = insertions;
    }

    private List<UserResponseDTO> generateAndSaveUsers(Collection<String> names) {
        List<UserCreateDTO> users = insertions.generateUsers(names);
        return insertions.saveAll(users, userService::save);
    }

    @Test
    void getAllMessages() {
        List<UserResponseDTO> users = generateAndSaveUsers(List.of("Alice", "Bob"));
        UserResponseDTO alice = users.get(0);
        UserResponseDTO bob = users.get(1);
        MessageCreateDTO message = insertions.generateMessage(bob.getId(), "Hello, Bob!");
        MessageResponseDTO messageResponse = messageService.postMessage(alice.getId(), message);

        assertTrue(messageService.getAllMessages().contains(messageResponse),
                "The message was saved but not displayed when querying all messages.");
    }

    @Test
    void getAllMessagesByReceiver() {
        List<UserResponseDTO> users = generateAndSaveUsers(List.of("User1", "User2"));
        UserResponseDTO u1 = users.get(0);
        UserResponseDTO u2 = users.get(1);
        int numberOfMessages = 4;
        List<MessageCreateDTO> messagesToU2 = insertions.generateMessages(u2.getId(), "Hello", numberOfMessages);
        List<MessageResponseDTO> savedMessages = insertions.saveAll(messagesToU2,
                m -> messageService.postMessage(u1.getId(), m));
        List<MessageResponseDTO> u2Received = messageService.getAllMessagesByReceiver(u2.getId());

        String err = "Sent " + numberOfMessages + " to User2, but can see " + u2Received.size();
        assertEquals(numberOfMessages, u2Received.size(), err);
        TestUtils.compareUnordered(u2Received, savedMessages);

    }

    @Test
    void getAllMessagesByReceiverPage() {
        List<UserResponseDTO> users = generateAndSaveUsers(List.of("User3", "User4"));
        UserResponseDTO u1 = users.get(0);
        UserResponseDTO u2 = users.get(1);
        int numberOfMessages = 5;
        int pageSize = 2;
        List<MessageCreateDTO> messagesToU2 = insertions.generateMessages(u2.getId(), "Hello", numberOfMessages);
        List<MessageResponseDTO> savedMessages = insertions.saveAll(messagesToU2,
                m -> messageService.postMessage(u1.getId(), m));

        List<MessageResponseDTO> page0 = messageService.getAllMessagesByReceiver(u2.getId(), 0, pageSize);
        assertEquals(page0.size(), pageSize);
        List<MessageResponseDTO> dynamicList = new ArrayList<>(page0);

        List<MessageResponseDTO> page1 = messageService.getAllMessagesByReceiver(u2.getId(), 1, pageSize);
        assertEquals(page1.size(), pageSize);
        dynamicList.addAll(page1);

        List<MessageResponseDTO> page2 = messageService.getAllMessagesByReceiver(u2.getId(), 2, pageSize);
        assertEquals(1, page2.size());
        dynamicList.addAll(page2);

        TestUtils.compareUnordered(savedMessages, dynamicList);

    }

    @Test
    void postMessage() {
        List<UserResponseDTO> users = generateAndSaveUsers(List.of("Carl", "David"));
        UserResponseDTO carl = users.get(0);
        UserResponseDTO david = users.get(1);
        String content = "Hello, David";
        MessageCreateDTO messageToDavid = insertions.generateMessage(david.getId(), content);
        MessageResponseDTO posted = messageService.postMessage(carl.getId(), messageToDavid);
        assertEquals(david.getId(), posted.getToId());
        assertNotNull(posted.getCreatedAt());
        assertEquals(content, posted.getContent());
    }
    @Test
    void testAnonymous() {
        List<UserResponseDTO> users = generateAndSaveUsers(List.of("Erica"));
        UserResponseDTO erica = users.get(0);
        String content = "Anonymous hello";
        MessageCreateDTO message = insertions.generateMessage(erica.getId(), content);

        messageService.postMessage(null, message);

        List<MessageResponseDTO> ericaMessages = messageService.getAllMessagesByReceiver(erica.getId());
        assertEquals(1, ericaMessages.size());
        assertEquals(content, ericaMessages.get(0).getContent());

    }
}