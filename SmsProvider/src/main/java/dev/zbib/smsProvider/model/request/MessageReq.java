package dev.zbib.smsProvider.model.request;

import lombok.Getter;

@Getter
public class MessageReq {
    String phoneNumber;
    String message;
    String language;
}
