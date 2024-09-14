package dev.zbib.server.service;

import dev.zbib.server.model.request.MessageRequest;
import dev.zbib.server.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMessageService {
    Message sendMessage(MessageRequest messageRequest);

    Message getMessageById(Long id);

    void deleteMessageById(Long id);

    Page<Message> getAllMessages(Pageable pageable);

    Message updateMessage(Long id, MessageRequest messageRequest);

}
