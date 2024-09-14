package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SmsSendingService {

    private final SmsProviderSelectorService providerSelector;

    @Autowired
    public SmsSendingService(SmsProviderSelectorService providerSelector) {
        this.providerSelector = providerSelector;
    }

    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        ISmsProviderService provider = providerSelector.getSmsProvider();
        return provider.sendSms(smsProviderRequest);
    }
}
