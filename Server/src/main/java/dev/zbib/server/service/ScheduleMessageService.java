package dev.zbib.server.service;

import dev.zbib.server.model.request.MessageRequest;
import dev.zbib.server.utils.CodeGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleMessageService {

    private final MessageService messageService;

    public ScheduleMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(fixedRate = 30000)
    private void sendScheduleMessage() {
        String code = CodeGenerator.generate6DigitCode();
        MessageRequest messageRequest = MessageRequest.builder()
                .message(code)
                .build();
        String messageCode  = messageService.sendMessage(messageRequest).getMessage();
        System.out.println("Your current code is +" + messageCode);
    }
}
