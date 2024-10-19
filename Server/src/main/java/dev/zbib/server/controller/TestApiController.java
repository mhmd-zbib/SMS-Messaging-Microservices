package dev.zbib.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class TestApiController {

    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("pong");
    }
}
