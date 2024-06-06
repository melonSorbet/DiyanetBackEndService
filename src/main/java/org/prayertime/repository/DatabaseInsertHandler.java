package org.prayertime.repository;

import jakarta.annotation.PostConstruct;
import org.prayertime.config.AppConfig;
import org.prayertime.model.DayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseInsertHandler {
    @Autowired
    private final AppConfig appConfig;

    public DatabaseInsertHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        DayDto dayDto = new DayDto();
        dayDto.setAsr("asrtime");
        dayDto.setGregorianDateShort("date");
        insertDays(List.of(dayDto), 100123);
        Class.forName("org.sqlite.JDBC");
    }

    public static void insertDays(List<DayDto> dayDtos, int cityId) {
        String raw_sql = "INSERT INTO Day(dateid,cityid, fajr) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/cool");
             PreparedStatement statement = conn.prepareStatement(raw_sql)) {
            System.out.println("hallo");
            for (int i = 0; i < dayDtos.size(); i++) {

                statement.setString(1, dayDtos.get(0).getGregorianDateShort());
                statement.setInt(2, cityId);
                statement.setString(3, dayDtos.get(0).getAsr());
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertCity(int cityId, String cityName, int countryId) {
        String rawSql = "INSERT INTO City(cityId,CityName,countryId) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/cool");
             PreparedStatement preparedStatement = conn.prepareStatement(rawSql)) {
            preparedStatement.setInt(1, cityId);
            preparedStatement.setString(2, cityName);
            preparedStatement.setInt(3, countryId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertEid() {

    }

    public static void insertDailyContent() {

    }

    public static void insertCountries() {

    }

    public static void insertWeekly() {

    }

    public static void insertRamadan() {

    }

    public static void main(String[] args) {
        ArrayList<DayDto> dayDtos = new ArrayList<>();
        DayDto dayDto = new DayDto();
        dayDto.setAsr("1450");
        dayDto.setGregorianDateShort("12312312");
        dayDtos.add(dayDto);
        DatabaseInsertHandler.insertCity(10110, "Berlin", 12300);
    }

}
