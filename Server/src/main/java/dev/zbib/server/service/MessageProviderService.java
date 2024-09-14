package dev.zbib.server.service;

import dev.zbib.server.dto.SmsProviderRequest;
import dev.zbib.server.provider.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageProviderService {

    private final ProviderSelector providerSelector;

    @Autowired
    public MessageProviderService(ProviderSelector providerSelector) {
        this.providerSelector = providerSelector;
    }

    public Mono<String> sendMessage(SmsProviderRequest smsProviderRequest) {
        SmsProvider provider = providerSelector.getSmsProvider();
        return provider.sendSms(smsProviderRequest);
    }
}
