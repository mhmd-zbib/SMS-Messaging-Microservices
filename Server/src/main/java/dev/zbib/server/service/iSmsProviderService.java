package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import reactor.core.publisher.Mono;

/**
 * <h2>SMS Providers Interface</h2>
 * <p>This interface is the got to for any new added providers, it got all the methods that the
 * providers will need to use in the future. Any added providers needs to implement this interface to follow
 * the organized rules for managing the logic</p>
 */
public interface iSmsProviderService {
    Mono<String> sendSms(SmsProviderRequest smsProviderRequest);
}
