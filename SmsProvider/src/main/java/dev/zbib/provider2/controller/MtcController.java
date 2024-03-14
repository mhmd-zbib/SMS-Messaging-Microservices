package dev.zbib.provider2.controller;

import dev.zbib.provider2.model.request.MessageReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mtc")
public class MtcController {

    @PostMapping("/sms")
    public ResponseEntity<String> sendCode(@RequestBody MessageReq messageReq) {
        System.out.println(messageReq.getPhoneNumber() +
                " says: " + messageReq.getMessage() +
                " talking in: " + messageReq.getLanguage());

        return ResponseEntity.ok(messageReq.getPhoneNumber() +
                " says: " + messageReq.getMessage() +
                " talking in: " + messageReq.getLanguage());
    }
}
