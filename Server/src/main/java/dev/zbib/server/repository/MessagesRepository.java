package dev.zbib.server.repository;

import dev.zbib.server.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Message, Integer> {
}
