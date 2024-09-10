package org.prayertime.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Getter
@Setter
@Component
@Configuration
public class AppConfig {
    @Value("${email}")
    private String email;
    @Value("${password}")
    private String password;

    public static AppConfig appConfigFactoryMethod() {
        AppConfig appConfig = new AppConfig();
        return appConfig;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

}
