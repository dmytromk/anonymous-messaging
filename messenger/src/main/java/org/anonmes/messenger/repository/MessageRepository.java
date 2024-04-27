package org.anonmes.messenger.repository;

import org.anonmes.messenger.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByToId(Long receiverId, Pageable pageable);
    List<Message> findAllByToId(Long receiverId);
}
