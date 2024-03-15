package dev.zbib.provider2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alfa")
public class AlfaController {

    @GetMapping("/sms/{phoneNumber}/{message}")
    public ResponseEntity<String> sendSms(@PathVariable String message, @PathVariable String phoneNumber) {
        System.out.println(phoneNumber + ": " + message);
        return ResponseEntity.ok(phoneNumber + ": " + message);
    }
}
