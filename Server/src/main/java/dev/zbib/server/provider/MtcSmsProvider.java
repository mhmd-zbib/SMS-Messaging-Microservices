package dev.zbib.server.provider;

import dev.zbib.server.dto.MtcSmsRequest;
import dev.zbib.server.dto.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MtcSmsProvider implements SmsProvider {

    private final WebClient webClient;

    @Value("${provider.mtc.url}")
    private String MtcSmsUrl;

    public MtcSmsProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(MtcSmsUrl).build();
    }

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {

        MtcSmsRequest request = MtcSmsRequest.builder()
                .message(smsProviderRequest.getMessage())
                .phoneNumber(smsProviderRequest.getPhoneNumber())
                .language(smsProviderRequest.getLanguage())
                .build();

        System.out.println(request.toString());

        webClient.post()
                .uri("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

        return Mono.just("OK");
    }
}
