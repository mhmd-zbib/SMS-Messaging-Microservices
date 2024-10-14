package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * <h2>Service for handling user's SMS actions with the provider</h2>
 */

@Service
public class SmsProviderService {

    private final SmsProviderFactory providerSelector;

    @Autowired
    public SmsProviderService(SmsProviderFactory providerSelector) {
        this.providerSelector = providerSelector;
    }

    /**
     * <h3>Sends SMS message to the provider</h3>
     * <p>This method takes {@link SmsProviderRequest} which got base content and params for all the requests of the user
     * it uses {@link SmsProviderFactory} to select the SMS provider and forwards the message</p>
     */
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        iSmsProviderService provider = providerSelector.getSmsProvider();
        return provider.sendSms(smsProviderRequest);
    }
}
