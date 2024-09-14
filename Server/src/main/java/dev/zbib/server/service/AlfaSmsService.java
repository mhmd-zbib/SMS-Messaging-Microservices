package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AlfaSmsService implements ISmsProviderService {



    @Value("${provider.alfa.url}")
    private String alfaSmsUrl;

    private final WebClient.Builder webClient;

    public AlfaSmsService(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(alfaSmsUrl);
    }


    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        return webClient.build().get()
                .uri("http://localhost:8010/")
                .retrieve()
                .bodyToMono(String.class);
    }
}
