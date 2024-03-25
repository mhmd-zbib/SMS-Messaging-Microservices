package dev.zbib.server.controller;

import dev.zbib.server.model.request.SmsProviderRequest;
import dev.zbib.server.service.QueueSmsProviderProducerService;
import dev.zbib.server.service.SmsMessageProviderService;
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

    private final SmsMessageProviderService messageProviderService;
    private final QueueSmsProviderProducerService queueSmsProviderService;

    @Autowired
    public SmsProviderController(SmsMessageProviderService messageProviderService, QueueSmsProviderProducerService queueSmsProviderService) {
        this.messageProviderService = messageProviderService;
        this.queueSmsProviderService = queueSmsProviderService;
    }

    @PostMapping
    public ResponseEntity<Mono<String>> sendMessage(@RequestBody SmsProviderRequest smsProviderRequest) {
        return ResponseEntity.ok(messageProviderService.sendSms(smsProviderRequest));
    }

    @PostMapping("/queue")
    public ResponseEntity<Mono<String>> sendSms() {
        return ResponseEntity.ok(queueSmsProviderService.sendSms());
    }

}
