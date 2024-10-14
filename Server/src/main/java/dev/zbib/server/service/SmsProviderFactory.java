package dev.zbib.server.service;

import dev.zbib.server.exception.Exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * <h2>Factory class for selecting an SMS provider</h2>
 * <p>This class select between two available SMS providers (Mtc and Alfa) and returns the chosen provider
 * It implements a simple random number (0 / 1) and uses switch case for selecting the sms</p>
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SmsProviderFactory {

    private final MtcSmsProviderService mtcSmsProvider;
    private final AlfaSmsProviderService alfaSmsProvider;
    private final Random random = new Random();


    public iSmsProviderService getSmsProvider() {
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return mtcSmsProvider;
            case 1:
                return alfaSmsProvider;
            case 2:
                throw new BadRequestException("We decided to give you an error");
            default:
                return null;
        }
    }
}
