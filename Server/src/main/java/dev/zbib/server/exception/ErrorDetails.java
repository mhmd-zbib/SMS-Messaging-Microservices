package dev.zbib.server.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ErrorDetails {
    private Date timestamp;
    private String code;
    private String message;
    private String details;
}
