package dev.zbib.server.service.impl;

import dev.zbib.server.dto.MtcSmsRequest;
import dev.zbib.server.dto.SmsProviderRequest;
import dev.zbib.server.service.SmsProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MtcSmsServiceImpl implements SmsProviderService {

    private final WebClient webClient;

    @Value("${provider.mtc.url}")
    private String MtcSmsUrl;

    public MtcSmsServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(MtcSmsUrl).build();
    }

    @Override
    public String sendSms(SmsProviderRequest smsProviderRequest) {

        MtcSmsRequest request = MtcSmsRequest.builder()
                .message(smsProviderRequest.getMessage())
                .phoneNumber(smsProviderRequest.getPhoneNumber())
                .language(smsProviderRequest.getLanguage())
                .build();

        return webClient.post()
                .uri("/message")
                .body(Mono.just(request), MtcSmsRequest.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
