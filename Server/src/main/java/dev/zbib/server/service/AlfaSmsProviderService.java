package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AlfaSmsProviderService implements ISmsProviderService {



    @Value("${provider.url}")
    private String providerUrl;

    @Value("${provider.routes.alfa}")
    private String alfaUrl;

    private final WebClient.Builder webClient;

    public AlfaSmsProviderService(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(providerUrl);
    }


    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        return webClient.build().get()
                .uri(alfaUrl)
                .retrieve()
                .bodyToMono(String.class);
    }
}
