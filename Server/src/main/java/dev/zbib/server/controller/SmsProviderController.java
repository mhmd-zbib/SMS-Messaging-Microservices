package dev.zbib.server.controller;

import dev.zbib.server.dto.SmsProviderRequest;
import dev.zbib.server.service.MessageProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/provider")
public class SmsProviderController {

    private final MessageProviderService messageProviderService;

    @Autowired
    public SmsProviderController(MessageProviderService messageProviderService) {
        this.messageProviderService = messageProviderService;

    }


    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SmsProviderRequest smsProviderRequest) {
        messageProviderService.sendMessage(smsProviderRequest);
        return ResponseEntity.ok("Message sent");
    }


}
