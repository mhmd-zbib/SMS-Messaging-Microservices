package dev.zbib.server.dto;

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
