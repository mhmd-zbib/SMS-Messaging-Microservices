package dev.zbib.server.controller;

import dev.zbib.server.dto.SmsProviderRequest;
import dev.zbib.server.service.impl.MtcSmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProviderController {

    private final MtcSmsServiceImpl mtcSmsService;

    public ProviderController(MtcSmsServiceImpl mtcSmsService) {
        this.mtcSmsService = mtcSmsService;
    }


    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SmsProviderRequest smsProviderRequest) {
        mtcSmsService.sendSms(smsProviderRequest);
        return ResponseEntity.ok("Message sent");
    }


}
