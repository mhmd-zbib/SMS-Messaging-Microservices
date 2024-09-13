package dev.zbib.server.dto;

import lombok.Getter;

@Getter
public class MessageRequest {
    String message;
    String phoneNumber;
    String language;
}
