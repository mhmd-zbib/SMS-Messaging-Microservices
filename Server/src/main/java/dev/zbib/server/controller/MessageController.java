package dev.zbib.server.controller;


import dev.zbib.server.model.entity.Message;
import dev.zbib.server.model.request.MessageRequest;
import dev.zbib.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest messageRequest) {
        Message message = messageService.sendMessage(messageRequest);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageById(@PathVariable Long id) {
        messageService.deleteMessageById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable Long id, @RequestBody MessageRequest messageRequest) {
        Message message = messageService.updateMessage(id, messageRequest);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<Page<Message>> getAllMessages(Pageable pageable) {
        Page<Message> messages = messageService.getAllMessages(pageable);
        return ResponseEntity.ok(messages);
    }

}
