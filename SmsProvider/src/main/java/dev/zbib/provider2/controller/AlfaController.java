package dev.zbib.provider2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alfa")
public class AlfaController {

    @GetMapping
    public ResponseEntity<String> message() {
        System.out.println("Sent!");
        return ResponseEntity.ok("Hello World");
    }
}
