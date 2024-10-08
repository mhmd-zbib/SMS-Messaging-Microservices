package dev.zbib.server.controller;

import dev.zbib.server.model.request.SmsProviderRequest;
import dev.zbib.server.service.ListenerQueueSmsService;
import dev.zbib.server.service.ScheduledQueueSmsService;
import dev.zbib.server.service.SmsProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/provider")
public class SmsProviderController {

    private final SmsProviderService messageProviderService;
    private final ListenerQueueSmsService listenerQueueSmsService;
    private final ScheduledQueueSmsService cronQueueSmsProviderService;

    @Autowired
    public SmsProviderController(SmsProviderService messageProviderService, ListenerQueueSmsService listenerQueueSmsService, ScheduledQueueSmsService cronQueueSmsProviderService) {
        this.messageProviderService = messageProviderService;
        this.listenerQueueSmsService = listenerQueueSmsService;
        this.cronQueueSmsProviderService = cronQueueSmsProviderService;
    }

    @PostMapping
    public ResponseEntity<Mono<String>> sendMessage(@RequestBody SmsProviderRequest smsProviderRequest) {
        return ResponseEntity.ok(messageProviderService.sendSms(smsProviderRequest));
    }

    @PostMapping("/queue/listener")
    public ResponseEntity<Mono<String>> sendMessageToListenerQueue(@RequestBody SmsProviderRequest smsProviderRequest) {
        return ResponseEntity.ok(listenerQueueSmsService.sendSms(smsProviderRequest));
    }


    @PostMapping("/queue/cron")
    public ResponseEntity<Mono<String>> sendMessageToCronQueue(@RequestBody SmsProviderRequest smsProviderRequest) {
        return ResponseEntity.ok(cronQueueSmsProviderService.sendSms(smsProviderRequest));
    }
}
