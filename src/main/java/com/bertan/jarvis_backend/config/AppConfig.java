package com.bertan.jarvis_backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jarvis.default")
@Getter
@Setter
public class AppConfig {

    private Account account = new Account();

    @Getter
    @Setter
    public static class Account {
        private Long id;
    }
}