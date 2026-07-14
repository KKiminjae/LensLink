package com.lenslink.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("kreamWebClient")
    public WebClient kreamWebClient() {

        ExchangeStrategies strategies =
                ExchangeStrategies.builder()
                        .codecs(configurer ->
                                configurer.defaultCodecs()
                                        .maxInMemorySize(10 * 1024 * 1024))
                        .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }
}
