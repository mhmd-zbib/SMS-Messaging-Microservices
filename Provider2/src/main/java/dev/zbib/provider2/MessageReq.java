package dev.zbib.provider2;

public record MessageReq(
        String phoneNumber,
        String message,
        String language
) {
}
