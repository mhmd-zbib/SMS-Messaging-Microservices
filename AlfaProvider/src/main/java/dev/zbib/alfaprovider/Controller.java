package dev.zbib.alfaprovider;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping
    public ResponseEntity<String> message() {
        System.out.println("Sent!");
        return ResponseEntity.ok("Hello World");
    }
}
