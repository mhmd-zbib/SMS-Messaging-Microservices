package dev.zbib.server.service;

import dev.zbib.server.model.request.MtcSmsRequest;
import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MtcSmsProviderService implements ISmsProviderService {


    private final WebClient.Builder webClient;

    @Value("${provider.url}")
    private String providerUrl;

    @Value("${provider.routes.mtc}")
    private String  mtcUrl;

    public MtcSmsProviderService(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(providerUrl);
    }

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {

        MtcSmsRequest request = MtcSmsRequest.builder()
                .message(smsProviderRequest.getMessage())
                .phoneNumber(smsProviderRequest.getPhoneNumber())
                .language(smsProviderRequest.getLanguage())
                .build();

        return webClient.build().post()
                .uri(mtcUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }
}
