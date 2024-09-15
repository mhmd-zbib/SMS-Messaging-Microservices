package dev.zbib.provider2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alfa")
public class AlfaController {

    @GetMapping("/sms")
    public ResponseEntity<String> sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        System.out.println(phoneNumber + ": " + message);
        return ResponseEntity.ok(phoneNumber + ": " + message);
    }
}
