package dev.zbib.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtcSmsRequest {
    String phoneNumber;
    String message;
    String language;
}
