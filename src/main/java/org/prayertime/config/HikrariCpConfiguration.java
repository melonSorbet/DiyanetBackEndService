package org.prayertime.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Setter
public class HikrariCpConfiguration {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;
    @Value("${spring.datasource.hikari.connection-timeout}")
    private int connectionTimeout;
    @Value("${spring.datasource.hikari.idle-timeout}")
    private int idleTimeout;
    @Value("${spring.datasource.hikari.max-lifetime}")
    private int maxLifetime;
    HikariConfig config;

    public static HikariConfig FactoryMethod(String jdbcUrl, String username, String password
            , String driverClassName, int maximumPoolSize
            , int minimumIdle, int connectionTimeout, int idleTimeout
            , int maxLifetime, DataSource dataSource) {

        HikariConfig hikrariCpConfiguration = new HikariConfig();
        hikrariCpConfiguration.setJdbcUrl(jdbcUrl);
        hikrariCpConfiguration.setUsername(username);
        hikrariCpConfiguration.setPassword(password);
        hikrariCpConfiguration.setDriverClassName(driverClassName);
        hikrariCpConfiguration.setMaximumPoolSize(maximumPoolSize);
        hikrariCpConfiguration.setMinimumIdle(minimumIdle);
        hikrariCpConfiguration.setConnectionTimeout(connectionTimeout);
        hikrariCpConfiguration.setIdleTimeout(idleTimeout);
        hikrariCpConfiguration.setMaxLifetime(maxLifetime);
        return hikrariCpConfiguration;
    }
    
}
