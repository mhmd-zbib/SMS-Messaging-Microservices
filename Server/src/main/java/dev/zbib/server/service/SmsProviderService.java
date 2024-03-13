package dev.zbib.server.service;

import dev.zbib.server.dto.SmsProviderRequest;

public interface SmsProviderService {

    String sendSms(SmsProviderRequest smsProviderRequest);

}
