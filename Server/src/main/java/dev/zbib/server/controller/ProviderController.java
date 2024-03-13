package dev.zbib.server.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class ProviderController {



    @PostMapping
    public ResponseEntity<String> postProvider1 (String phone) {
        return null;
    }

    @GetMapping
    public ResponseEntity<String> getProvider2 () {
        return null;
    }

}
