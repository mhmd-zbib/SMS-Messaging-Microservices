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

    @Value("${provider.url}")
    private String providerUrl;
    @Value("${provider.routes.alfa}")
    private String alfaUrl;

    @Autowired
    public AlfaSmsProviderService(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(this.providerUrl).build();
    }


    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        return webClient.get()
                .uri("http://localhost/alfa/sms")
                .retrieve()
                .bodyToMono(String.class);
    }
}
