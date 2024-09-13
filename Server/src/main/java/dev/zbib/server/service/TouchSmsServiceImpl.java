package dev.zbib.server.service;

import dev.zbib.server.dto.TouchSmsRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TouchSmsServiceImpl implements SmsProviderService {

    private static final String touchUrl = "http://localhost:8020";

    private final WebClient webClient;

    public TouchSmsServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(touchUrl).build();
    }

    @Override
    public Mono sendSms(Object request) {
        return webClient.post()
                .uri("/message")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TouchSmsRequest.class);
    }
}
