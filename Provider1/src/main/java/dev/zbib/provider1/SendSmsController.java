package dev.zbib.provider1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SendSmsController {

    @GetMapping("/send/{phone}/{message}")
    public ResponseEntity<String> sendSms(
            @PathVariable String phone,
            @PathVariable String message
    ) {
        System.out.println(phone + " says " + message);
        return ResponseEntity.ok(phone + " says " + message);
    }
}
