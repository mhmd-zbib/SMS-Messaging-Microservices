package dev.zbib.server.provider;

import dev.zbib.server.dto.SmsProviderRequest;
import reactor.core.publisher.Mono;

public interface SmsProvider {

    Mono<String> sendSms(SmsProviderRequest smsProviderRequest);

}
