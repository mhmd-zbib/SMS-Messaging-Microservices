package dev.zbib.server.service;

import dev.zbib.server.model.request.MtcSmsJsonRequest;
import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * <h2>Mtc provider actions handler</h2>
 * <p>This service implements the ISmsProviderService taking all the methods that all the providers do,
 * and here it uses them depending on its context. Using bean {@link WebClient} bean in config.</p>
 */

@Service
public class MtcSmsProviderService implements ISmsProviderService {


    private final WebClient webClient;
    @Value("${provider.routes.mtc}")
    private String mtcUrl;

    @Autowired
    public MtcSmsProviderService(WebClient webClient) {
        this.webClient = webClient;
    }


    /**
     * <h3>Mtc SMS message sender</h3>
     * <p>This service handles sending sms to the mtc provider. It builds the Mtc SMS request as
     * a json (check {@link MtcSmsJsonRequest} and calls webclient.post() adding the content and
     * the value and handling the errors.</p>
     */
    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        MtcSmsJsonRequest request = MtcSmsJsonRequest.builder()
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
