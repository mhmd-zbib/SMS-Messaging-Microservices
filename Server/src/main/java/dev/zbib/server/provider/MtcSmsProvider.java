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


    private final WebClient.Builder webClient;
    @Value("${provider.mtc.url}")
    private String mtcSmsUrl;

    public MtcSmsProvider(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {

        MtcSmsRequest request = MtcSmsRequest.builder()
                .message(smsProviderRequest.getMessage())
                .phoneNumber(smsProviderRequest.getPhoneNumber())
                .language(smsProviderRequest.getLanguage())
                .build();

        return webClient.build().post()
                .uri("http://localhost:8020")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);

    }
}
