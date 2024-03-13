package dev.zbib.server.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TouchSmsRequest {
    String phoneNumber;
    String message;
    String language;
}
