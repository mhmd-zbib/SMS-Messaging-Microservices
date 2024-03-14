package dev.zbib.server.service;

import dev.zbib.server.provider.AlfaSmsProvider;
import dev.zbib.server.provider.MtcSmsProvider;
import dev.zbib.server.provider.SmsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ProviderSelector {

    private final MtcSmsProvider mtcSmsProvider;
    private final AlfaSmsProvider alfaSmsProvider;
    private final Random random = new Random();

    @Autowired
    public ProviderSelector(MtcSmsProvider mtcSmsProvider, AlfaSmsProvider alfaSmsProvider) {
        this.mtcSmsProvider = mtcSmsProvider;
        this.alfaSmsProvider = alfaSmsProvider;
    }

    public  SmsProvider getSmsProvider() {
        int choice = random.nextInt(2) + 1; // Generates 1 or 2
        if (choice == 1) {
            return mtcSmsProvider;
        } else {
            return alfaSmsProvider;
        }
    }

}
