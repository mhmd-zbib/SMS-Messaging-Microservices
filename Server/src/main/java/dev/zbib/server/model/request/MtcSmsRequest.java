package dev.zbib.server.model.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MtcSmsRequest {
    String phoneNumber;
    String message;
    String language;
}
