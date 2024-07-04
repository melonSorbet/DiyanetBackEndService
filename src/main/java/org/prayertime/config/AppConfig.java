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
    @Value("${database.url}")
    private String databasePath;
    @Value("${database.password}")
    private String databasePassword;
    @Value("${database.name}")
    private String databaseName;

    public static AppConfig appConfigFactoryMethod(String databasePath) {
        AppConfig appConfig = new AppConfig();
        appConfig.setDatabasePath(databasePath);
        return appConfig;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

}
