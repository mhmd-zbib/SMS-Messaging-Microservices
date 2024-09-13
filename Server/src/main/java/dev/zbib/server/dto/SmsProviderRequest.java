package dev.zbib.server.dto;

import lombok.Getter;

@Getter
public class SmsProviderRequest {

    private String message;
    private String phoneNumber;
    private String language;
    
}
