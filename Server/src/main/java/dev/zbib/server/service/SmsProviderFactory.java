package dev.zbib.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SmsProviderFactory {

    private final MtcSmsProviderService mtcSmsProvider;
    private final AlfaSmsProviderService alfaSmsProvider;

    private final Random random = new Random();

    @Autowired
    public SmsProviderFactory(MtcSmsProviderService mtcSmsProvider, AlfaSmsProviderService alfaSmsProvider) {
        this.mtcSmsProvider = mtcSmsProvider;
        this.alfaSmsProvider = alfaSmsProvider;
    }

    public ISmsProviderService getSmsProvider() {
        int choice = random.nextInt(2);
        switch (choice) {
            case 0:
                return mtcSmsProvider;
            case 1:
                return alfaSmsProvider;
            default:
                    return null;
        }
    }
}
