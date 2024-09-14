package dev.zbib.server.controller;

import dev.zbib.server.model.request.SmsProviderRequest;
import dev.zbib.server.service.SmsSendingService;
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

    private final SmsSendingService messageProviderService;

    @Autowired
    public SmsProviderController(SmsSendingService messageProviderService) {
        this.messageProviderService = messageProviderService;

    }

    @PostMapping
    public ResponseEntity<Mono<String>> sendMessage(@RequestBody SmsProviderRequest smsProviderRequest) {
        return ResponseEntity.ok(messageProviderService.sendSms(smsProviderRequest));
    }


}
