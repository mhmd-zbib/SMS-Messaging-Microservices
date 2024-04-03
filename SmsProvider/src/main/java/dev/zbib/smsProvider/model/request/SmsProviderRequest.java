package dev.zbib.smsProvider.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SmsProviderRequest {
    private String message;
    private String phoneNumber;
    private String language;
}
