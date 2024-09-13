package dev.zbib.server.provider;

import dev.zbib.server.dto.SmsProviderRequest;
import org.springframework.stereotype.Service;

@Service
public class AlfaSmsProvider implements SmsProvider {


    @Override
    public String sendSms(SmsProviderRequest smsProviderRequest) {
        return "";
    }
}
