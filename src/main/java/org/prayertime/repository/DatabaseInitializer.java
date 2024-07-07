package org.prayertime.repository;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.prayertime.config.AppConfig;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DatabaseInitializer {
    private final AppConfig appConfig;
    @Setter
    DataSource dataSource;

    public DatabaseInitializer(AppConfig appConfig, DataSource dataSource) {
        this.appConfig = appConfig;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initializeDatabase() throws ClassNotFoundException {
        createDatabase();
        initializeCityTable();
        initializeCountryTable();
        initializeDaysTable();
        initializeDayContentTable();
    }

    private void initializeDaysTable() {

        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Day (\n" +
                    "  dateId INTEGER PRIMARY KEY,\n" +
                    "  cityId INTEGER,\n" +
                    "  fajr INTEGER,\n" +
                    "  dhuhr INTEGER,\n" +
                    "  asr INTEGER,\n" +
                    "  maghrib INTEGER,\n" +
                    "  isha INTEGER,\n" +
                    "  sunrise INTEGER,\n" +
                    "  FOREIGN KEY (cityId) REFERENCES City(cityId)\n" +
                    ");\n";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeCountryTable() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Country (\n" +
                    "  countryId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  countryName VARCHAR(100)\n" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeCityTable() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS City (\n" +
                    "  cityId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  cityName VARCHAR(30),\n" +
                    "  countryId INTEGER,\n" +
                    "  FOREIGN KEY (countryId) REFERENCES Country(countryId)\n" +
                    ")";
            stmt.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDayContentTable() {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS DailyContent (\n" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  dateId INTEGER,\n" +
                    "  verse VARCHAR(30),\n" +
                    "  verseSource VARCHAR(30),\n" +
                    "  hadith VARCHAR(30),\n" +
                    "  hadithSource VARCHAR(30),\n" +
                    "  pray VARCHAR(30),\n" +
                    "  prayerSource VARCHAR(30),\n" +
                    "  FOREIGN KEY (dateId) REFERENCES Day(dateId)\n" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDatabase() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = dataSource.getConnection()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
