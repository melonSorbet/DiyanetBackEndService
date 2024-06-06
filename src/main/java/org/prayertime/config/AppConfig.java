package org.prayertime.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
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
}
