package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * <h2>Alfa provider actions handler</h2>
 * <p>This service implements the ISmsProviderService taking all the methods that all the providers do,
 * and here it uses them depending on its context. Using bean {@link WebClient} bean in config.</p>
 */
@Service
public class AlfaSmsProviderService implements iSmsProviderService {

    private final WebClient webClient;




    @Autowired
    public AlfaSmsProviderService(WebClient webClient) {
        this.webClient = webClient;
    }


    /**
     * <h3>Alfa SMS message sender</h3>
     * <p>This service handles sending sms to the alfa provider. It passes the request params to the
     * get api from the smsProviderRequest</p>
     */
    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("alfa")
                        .queryParam("phoneNumber", smsProviderRequest.getPhoneNumber())
                        .queryParam("message", smsProviderRequest.getMessage())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("ALFA [ERROR] " + error.getMessage()));
    }
}
