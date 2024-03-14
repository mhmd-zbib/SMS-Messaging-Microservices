package dev.zbib.server.provider;

import dev.zbib.server.dto.SmsProviderRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AlfaSmsProvider implements SmsProvider {


    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        return  Mono.just("Reached me here" );
    }
}
