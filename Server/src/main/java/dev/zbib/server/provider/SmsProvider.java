package dev.zbib.server.provider;

import dev.zbib.server.dto.SmsProviderRequest;

public interface SmsProvider {

    String sendSms(SmsProviderRequest smsProviderRequest);

}
