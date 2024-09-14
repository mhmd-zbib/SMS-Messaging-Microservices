package dev.zbib.server.service;

import dev.zbib.server.dto.MessageRequest;
import dev.zbib.server.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message sendMessage(MessageRequest messageRequest);

    Message getMessage(Long id);

    void deleteMessage(Long id);

    Page<Message> getAllMessages(Pageable pageable);

    Message updateMessage(Long id, MessageRequest messageRequest);

}
