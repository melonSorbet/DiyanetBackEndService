package org.prayertime.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {
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
    private final HikariConfig config = new HikariConfig();
    private HikariDataSource dataSource;

    @PostConstruct
    public void setDataSource() {
        config.setJdbcUrl(this.jdbcUrl);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setDriverClassName(this.driverClassName);
        config.setMaximumPoolSize(this.maximumPoolSize);
        config.setMinimumIdle(this.minimumIdle);
        config.setConnectionTimeout(this.connectionTimeout);
        config.setIdleTimeout(this.idleTimeout);
        config.setMaxLifetime(this.maxLifetime);
        this.dataSource = new HikariDataSource(this.config);
    }

    @Bean
    public DataSource dataSource() {
        return this.dataSource;
    }

    public static DataSource createDataSource(int maxLifetime, int idleTimeout, int connectionTimeout, int minimumIdle, int maximumPoolSize, String driverClassName, String password, String username, String jdbcUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);

        return new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}