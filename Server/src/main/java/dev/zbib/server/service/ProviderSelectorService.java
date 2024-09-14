package dev.zbib.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProviderSelectorService {

    private final MtcSmsService mtcSmsProvider;
    private final AlfaSmsService alfaSmsProvider;
    private final Random random = new Random();

    @Autowired
    public ProviderSelectorService(MtcSmsService mtcSmsProvider, AlfaSmsService alfaSmsProvider) {
        this.mtcSmsProvider = mtcSmsProvider;
        this.alfaSmsProvider = alfaSmsProvider;
    }

    public ISmsProviderService getSmsProvider() {
        int choice = random.nextInt(2) + 1; // Generates 1 or 2
        if (choice == 1) {
            return mtcSmsProvider;
        } else {
            return alfaSmsProvider;
        }
    }

}
