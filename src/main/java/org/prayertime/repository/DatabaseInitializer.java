package org.prayertime.repository;

import org.prayertime.config.AppConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DatabaseInitializer {

    private AppConfig appConfig;

    public DatabaseInitializer(AppConfig appConfig){
        this.appConfig = appConfig;
    }
    public static void initializeDaysTable(String url, String filename){
        try(Connection conn = DriverManager.getConnection(url + filename); Statement stmt = conn.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS Day (\n" +
                    "  dateId DATE PRIMARY KEY,\n" +
                    "  cityId INTEGER,\n" +
                    "  FOREIGN KEY (cityId) REFERENCES City(cityId)\n" +
                    ");\n";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initializeCountryTable(String url, String filename){
        try(Connection conn = DriverManager.getConnection(url + filename); Statement stmt = conn.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS Country (\n" +
                    "  countryId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  countryName VARCHAR(100)\n" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initializeCityTable(String url, String filename){
        try(Connection conn = DriverManager.getConnection(url + filename); Statement stmt = conn.createStatement()){
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
    public static void initializeDayContentTable(String url, String filename){
        try(Connection conn = DriverManager.getConnection(url + filename); Statement stmt = conn.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS DailyContent (\n" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "  dateId DATE,\n" +
                    "  cityId INTEGER,\n" +
                    "  verse VARCHAR(30),\n" +
                    "  verseSource VARCHAR(30),\n" +
                    "  hadith VARCHAR(30),\n" +
                    "  hadithSource VARCHAR(30),\n" +
                    "  pray VARCHAR(30),\n" +
                    "  FOREIGN KEY (dateId, cityId) REFERENCES Day(dateId, cityId)\n" +
                    ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createDatabase(String fileName, String url) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try(Connection conn = DriverManager.getConnection(url + fileName)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws ClassNotFoundException {

        String url = "jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/";
        createDatabase("cool", url);
        initializeCityTable(url,"cool");
        initializeCountryTable(url,"cool");
        initializeDaysTable(url,"cool");
        initializeDayContentTable(url,"cool");

    }
}
