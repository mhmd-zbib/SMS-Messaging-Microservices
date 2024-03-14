package dev.zbib.server.service.impl;

import dev.zbib.server.dto.MessageRequest;
import dev.zbib.server.model.Message;
import dev.zbib.server.repository.MessagesRepository;
import dev.zbib.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public Message sendMessage(MessageRequest messageRequest) {
        Message message = Message.builder()
                .message(messageRequest.getMessage())
                .phoneNumber(messageRequest.getPhoneNumber())
                .build();

        return messagesRepository.save(message);

    }

    @Override
    public Message getMessageById(Long id) {
        return messagesRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMessageById(Long id) {
        Message message = getMessageById(id);
        messagesRepository.delete(message);
    }

    @Override
    public Page<Message> getAllMessages(Pageable pageable) {
        return messagesRepository.findAll(pageable);
    }

    @Override
    public Message updateMessage(Long id, MessageRequest messageRequest) {
        Message message = getMessageById(id);
        message.setMessage(messageRequest.getMessage());
        message.setPhoneNumber(messageRequest.getPhoneNumber());
        return messagesRepository.save(message);

    }
}
