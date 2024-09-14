package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageProviderService {

    private final ProviderSelectorService providerSelector;

    @Autowired
    public MessageProviderService(ProviderSelectorService providerSelector) {
        this.providerSelector = providerSelector;
    }

    public Mono<String> sendMessage(SmsProviderRequest smsProviderRequest) {
        ISmsProviderService provider = providerSelector.getSmsProvider();
        return provider.sendSms(smsProviderRequest);
    }
}
