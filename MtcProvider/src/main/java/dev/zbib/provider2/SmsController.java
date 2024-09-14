package dev.zbib.provider2;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SmsController {


    @PostMapping
    public ResponseEntity<String> sendCode(@RequestBody MessageReq messageReq) {
        System.out.println(messageReq.getPhoneNumber() +
                " says: " + messageReq.getMessage() +
                " talking in: " + messageReq.getLanguage());

        return ResponseEntity.ok(messageReq.getPhoneNumber() +
                " says: " + messageReq.getMessage() +
                " talking in: " + messageReq.getLanguage());
    }

}
