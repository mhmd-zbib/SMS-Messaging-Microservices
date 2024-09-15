package dev.zbib.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * <h2>Configuration for webclient interactions</h2>
 * <p>Beans for different APIs to reuse through the cycle of the application</p>
 */

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
