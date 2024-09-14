package dev.zbib.server.utils;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;


public class CodeGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generate6DigitCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
