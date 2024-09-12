package dev.zbib.provider2;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class SmsController {

    @PostMapping
    public ResponseEntity<String> sendCode(@RequestBody MessageReq messageReq) {
        System.out.println(messageReq.phoneNumber() +
                " says: " + messageReq.message() +
                " talking in: " + messageReq.language());

        return ResponseEntity.ok(messageReq.phoneNumber() +
                " says: " + messageReq.message() +
                " talking in: " + messageReq.language());
    }

}
