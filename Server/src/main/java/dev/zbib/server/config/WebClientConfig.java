package dev.zbib.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${provider.url}")
    private String smsProviderUrl;

    @Bean
    public WebClient webClient() {
        return  WebClient.builder()
                .baseUrl(smsProviderUrl)
                .build();
    }
}
