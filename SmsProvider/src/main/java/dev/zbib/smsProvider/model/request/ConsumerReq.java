package dev.zbib.smsProvider.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ConsumerReq {
    private String phoneNumber;
    private String message;
}
