package dev.zbib.smsProvider.model.request;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SmsProviderRequest implements Serializable {
    private String message;
    private String phoneNumber;
    private String language;
}
