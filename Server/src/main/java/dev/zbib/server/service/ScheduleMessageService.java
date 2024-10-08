package dev.zbib.server.service;

import dev.zbib.server.model.request.MessageRequest;
import dev.zbib.server.utils.CodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * <h2>Service for scheduled messages actions</h2>
 * <p>Handles the schedules tasks for the message</p>
 */

@Service
public class ScheduleMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleMessageService.class);
    private final MessageService messageService;

    public ScheduleMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * <h3>Internal server scheduled messages</h3>
     * <p>This service creates a code every 3 min and saves it on the database with logging the value</p>
     */
    @Scheduled(fixedRate = 300000)
    private void sendScheduleMessage() {
        String code = CodeGenerator.generate6DigitCode();
        MessageRequest messageRequest = MessageRequest.builder()
                .message(code)
                .build();
        String messageCode = messageService.sendMessage(messageRequest).getMessage();
        LOGGER.info("Your new code is: {}", messageCode);
    }
}
