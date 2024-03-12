package dev.zbib.server;

public record MessageRequest(
        String message,
        String phoneNumber,
        String language
) {
}
