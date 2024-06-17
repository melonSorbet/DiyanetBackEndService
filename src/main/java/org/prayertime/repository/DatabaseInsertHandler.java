package org.prayertime.repository;

import org.prayertime.config.AppConfig;
import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DailyContentDto;
import org.prayertime.model.DayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DatabaseInsertHandler {
    @Autowired
    private final AppConfig appConfig;

    public DatabaseInsertHandler(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public static void insertDays(DayDto[] dayDtos, int cityId) {
        String raw_sql = "INSERT INTO Day(dateid,cityid, fajr,dhuhr,asr,maghrib,isha,sunrise) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement statement = conn.prepareStatement(raw_sql)) {
            for (DayDto dayDto : dayDtos) {
                statement.setInt(1, dayDto.getGregorianDateShort());
                statement.setInt(2, cityId);
                statement.setInt(3, dayDto.getFajr());
                statement.setInt(4, dayDto.getDhuhr());
                statement.setInt(5, dayDto.getAsr());
                statement.setInt(6, dayDto.getMaghrib());
                statement.setInt(7, dayDto.getIsha());
                statement.setInt(7, dayDto.getSunrise());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertCity(CityDto[] cityDto, int countryId) {
        String rawSql = "INSERT INTO City(cityId,CityName,countryId) VALUES(?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement preparedStatement = conn.prepareStatement(rawSql)) {
            for (int i = 0; i < cityDto.length; i++) {
                preparedStatement.setInt(1, cityDto[i].getId());
                preparedStatement.setString(2, cityDto[i].getCityName());
                preparedStatement.setInt(3, countryId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertDailyContent(DailyContentDto dailyContentDto) {
        String rawSql = "INSERT INTO DailyContent(dateId,verse,verseSource,hadith,hadithSource,pray) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement preparedStatement = conn.prepareStatement(rawSql)) {

            preparedStatement.setDate(1, new Date(12304));
            preparedStatement.setString(2, dailyContentDto.getVerse());
            preparedStatement.setString(3, dailyContentDto.getVerseSource());
            preparedStatement.setString(4, dailyContentDto.getHadith());
            preparedStatement.setString(5, dailyContentDto.getHadithSource());
            preparedStatement.setString(6, dailyContentDto.getPrayer());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertCountries(CountryDto[] countryDto) {
        String rawSql = "INSERT INTO Country(countryId, countryName) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement preparedStatement = conn.prepareStatement(rawSql)) {
            for (int i = 0; i < countryDto.length; i++) {
                preparedStatement.setInt(1, countryDto[i].getId());
                preparedStatement.setString(2, countryDto[i].getCountryName());
                preparedStatement.executeUpdate();
            }

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
