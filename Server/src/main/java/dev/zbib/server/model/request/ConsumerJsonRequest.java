package dev.zbib.server.model.request;

import lombok.Builder;

@Builder
public class ConsumerJsonRequest {

    private String phoneNumber;

    private String message;

}
