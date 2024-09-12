package dev.zbib.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/messageProvider")
public class Controller {

    private static final String provider1Url = "http://localhost:8020/message";
    private static final String provider2Url = "http://localhost:8010/sms/send/{phone}/{message}";

    // Autowired webclient to not call a new one every time (for later)
    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     *  In this scenario the client chooses what provider to use by passing the req param in the url
     *  if no req param was passed we consider it provider1 and call the api
     *
     *  provider1 is a get req that takes path var of message and phone number only
     *  provider2 is a post req that takes a req body of message, phone, and language
     *
     */
    @PostMapping
    public Mono<String> sendMessage(@RequestBody MessageRequest messageRequest,
                                    @RequestParam(value = "provider", required = false) String provider) {

        if (provider == null || provider.equals("1")) {
            String messagePath = messageRequest.message();
            String phonePath = messageRequest.phoneNumber();

            return webClientBuilder.build().get()
                    .uri(provider2Url, phonePath, messagePath)
                    .retrieve()
                    .bodyToMono(String.class);
        }
        if (provider.equals("2")) {
            return webClientBuilder.build()
                    .post()
                    .uri(provider1Url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(messageRequest)
                    .retrieve()
                    .bodyToMono(String.class);
        }
        return null;
    }
}
