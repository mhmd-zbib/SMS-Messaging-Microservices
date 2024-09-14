package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import reactor.core.publisher.Mono;

public interface ISmsProviderService {

    Mono<String> sendSms(SmsProviderRequest smsProviderRequest);

}
