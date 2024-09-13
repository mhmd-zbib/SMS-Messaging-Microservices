package dev.zbib.server.service;

import reactor.core.publisher.Mono;

public interface SmsProviderService<R, T> {
    Mono<R> sendSms(T request);
}
