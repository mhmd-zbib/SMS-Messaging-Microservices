package dev.zbib.server.service;

import dev.zbib.server.model.request.MtcSmsRequest;
import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MtcSmsProviderService implements ISmsProviderService {


    private final WebClient webClient;
    @Value("${provider.routes.mtc}")
    private String mtcUrl;

    @Autowired
    public MtcSmsProviderService(WebClient webClient) {
        this.webClient = webClient;
    }


    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        MtcSmsRequest request = MtcSmsRequest.builder()
                .message(smsProviderRequest.getMessage())
                .phoneNumber(smsProviderRequest.getPhoneNumber())
                .language(smsProviderRequest.getLanguage())
                .build();

        return webClient.post()
                .uri(mtcUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.err.println("MTC [ERROR] " + error.getMessage()));
    }
}
