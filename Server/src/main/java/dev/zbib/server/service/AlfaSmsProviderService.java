package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AlfaSmsProviderService implements ISmsProviderService {

    private final WebClient webClient;


    @Value("${provider.routes.alfa}")
    private String alfaUrl;

    @Autowired
    public AlfaSmsProviderService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(alfaUrl)
                        .queryParam("phoneNumber", smsProviderRequest.getPhoneNumber())
                        .queryParam("message", smsProviderRequest.getMessage())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("ALFA [ERROR] " + error.getMessage()));
    }
}
