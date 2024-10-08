package dev.zbib.server.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public class MtcSmsJsonRequest  {

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("message")
    private String message;

    @JsonProperty("language")
    private String language;

}
