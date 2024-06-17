package org.prayertime.repository;

import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DailyContentDto;
import org.prayertime.model.DayDto;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseSelectHandler {
    public List<DayDto> selectMonthlyPrayerTime() {
        String rawSql = "Select dateId, fajr, dhuhr, asr, maghrib, isha, sunrise FROM Day";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/database");
             Statement statement = conn.createStatement(); var result = statement.executeQuery(rawSql)) {
            List<DayDto> dayDtos = new ArrayList<>();

            while (result.next()) {
                dayDtos.add(new DayDto(result.getInt(1), result.getInt(2), result.getInt(2), result.getInt(3), result.getInt(4), result.getInt(5), result.getInt(6)));
            }
            return dayDtos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DayDto> selectDaysFromCity(int cityId) {
        String rawSql = "Select dateId, fajr, dhuhr, asr, maghrib, isha, sunrise FROM Day WHERE cityId == ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement statement = conn.prepareStatement(rawSql)) {
            statement.setInt(1, cityId);
            var result = statement.executeQuery();
            List<DayDto> dayDtos = new ArrayList<>();

            while (result.next()) {
                dayDtos.add(new DayDto(result.getInt(1), result.getInt(2), result.getInt(2), result.getInt(3), result.getInt(4), result.getInt(5), result.getInt(6)));
            }
            return dayDtos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DayDto> selectDaysFromCityBetweenTimes(int cityId, int firesulttDate, int lastDate) {
        String rawSql = "Select dateId, fajr, dhuhr, asr, maghrib, isha, sunrise FROM Day WHERE cityId == ? AND dateId >= ? AND dateId <= ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement statement = conn.prepareStatement(rawSql)) {

            statement.setInt(1, cityId);
            statement.setInt(2, firesulttDate);
            statement.setInt(3, lastDate);

            var result = statement.executeQuery();

            List<DayDto> dayDtos = new ArrayList<>();

            while (result.next()) {
                dayDtos.add(new DayDto(result.getInt(1),
                        result.getInt(2), result.getInt(2),
                        result.getInt(3), result.getInt(4),
                        result.getInt(5), result.getInt(6)));
            }
            return dayDtos;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CityDto> selectCitiesFromCountry(int countryId) {
        String rawSql = "SELECT cityId , cityName, countryId From City WHERE countryId == ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement statement = conn.prepareStatement(rawSql)) {
            statement.setInt(1, countryId);
            var result = statement.executeQuery();
            List<CityDto> cities = new ArrayList<>();

            while (result.next()) {
                cities.add(new CityDto(result.getInt(1), "", result.getString(2)));
            }
            return cities;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CountryDto> selectCountries() {
        String rawSql = "SELECT countryId, countryName From Country";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement statement = conn.prepareStatement(rawSql)) {
            var result = statement.executeQuery();
            List<CountryDto> cities = new ArrayList<>();

            while (result.next()) {
                cities.add(new CountryDto(result.getInt(1), "", result.getString(2)));
            }
            return cities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DailyContentDto selectDailContentDto(int dateId) {
        String rawSql = "SELECT id, dateid, verse, versesource, hadith, hadithsource, pray, prayersource From DailyContent WHERE dateId == ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/home/user/Development/PrayerTimeProject/DiyanetBackEndService/src/main/java/org/prayertime/database/data");
             PreparedStatement statement = conn.prepareStatement(rawSql)) {
            var result = statement.executeQuery();

            if (result.next()) {
                return new DailyContentDto(result.getInt(1), result.getInt(2),
                        result.getString(3), result.getString(4),
                        result.getString(5), result.getString(6),
                        result.getString(7), result.getString(8));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
