package dev.zbib.server.model.request;

import lombok.Getter;

@Getter
public class MessageRequest {
    String message;
    String phoneNumber;
    String language;
}
