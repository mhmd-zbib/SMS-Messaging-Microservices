package dev.zbib.smsProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsProviderApplication.class, args);
        System.out.println("Welcome to the provider");

    }


}
