package org.prayertime.repository;

import org.junit.jupiter.api.Test;
import org.prayertime.config.AppConfig;

import java.sql.*;

class DatabaseInitializerTest {
    private final AppConfig appConfig = AppConfig.appConfigFactoryMethod("jdbc:sqlite::memory:");
    private final DatabaseInitializer databaseInitializer = new DatabaseInitializer(appConfig, hikrariCpConfiguration);

    @Test
    void initializeDatabase() throws ClassNotFoundException {
        databaseInitializer.initializeDatabase();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:"); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type='table' and name='day'");
            System.out.println(rs.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}