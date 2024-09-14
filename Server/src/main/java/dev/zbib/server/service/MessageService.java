package dev.zbib.server.service;

import dev.zbib.server.model.entity.Message;
import dev.zbib.server.model.request.MessageRequest;
import dev.zbib.server.repository.MessagesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessagesRepository messagesRepository;

    public MessageService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Transactional
    public Message sendMessage(MessageRequest messageRequest) {
        Message message = Message.builder()
                .message(messageRequest.getMessage())
                .phoneNumber(messageRequest.getPhoneNumber())
                .build();

        return messagesRepository.save(message);

    }

    public Message getMessageById(Long id) {
        return messagesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteMessageById(Long id) {
        Message message = getMessageById(id);
        messagesRepository.delete(message);
    }

    public Page<Message> getAllMessages(Pageable pageable) {
        return messagesRepository.findAll(pageable);
    }

    @Transactional
    public Message updateMessage(Long id, MessageRequest messageRequest) {
        Message message = getMessageById(id);
        message.setMessage(messageRequest.getMessage());
        message.setPhoneNumber(messageRequest.getPhoneNumber());
        return messagesRepository.save(message);

    }
}
