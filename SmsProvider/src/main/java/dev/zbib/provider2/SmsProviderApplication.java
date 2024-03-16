package dev.zbib.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsProviderApplication.class, args);
        System.out.println("Welcome to the provider");

    }


}
