package org.prayertime.repository;

import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DailyContentDto;
import org.prayertime.model.DayDto;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseSelectHandler {
    private final DataSource dataSource;

    public DatabaseSelectHandler(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<DayDto> selectDaysFromCityBetweenTimes(int cityId, int firesulttDate, int lastDate) {
        String rawSql = "Select dateId, fajr, dhuhr, asr, maghrib, isha, sunrise FROM Day WHERE cityId == ? AND dateId >= ? AND dateId <= ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(rawSql)) {

            statement.setInt(1, cityId);
            statement.setInt(2, firesulttDate);
            statement.setInt(3, lastDate);

            var result = statement.executeQuery();

            List<DayDto> dayDtos = new ArrayList<>();

            while (result.next()) {
                dayDtos.add(new DayDto(result.getInt(1),
                        result.getInt(2), result.getInt(3),
                        result.getInt(4), result.getInt(5),
                        result.getInt(6), result.getInt(7)));
            }
            return dayDtos;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CityDto> selectCitiesFromCountry(int countryId) {
        String rawSql = "SELECT cityId , cityName, countryId From City WHERE countryId == ?";
        try (Connection conn = dataSource.getConnection();
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
        try (Connection conn = dataSource.getConnection();
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

    public DailyContentDto selectDailyContentDto(int dateId) {
        String rawSql = "SELECT id, dateid, verse, versesource, hadith, hadithsource, pray, prayersource From DailyContent WHERE dateId == ?";
        try (Connection conn = dataSource.getConnection();

             PreparedStatement statement = conn.prepareStatement(rawSql)) {
            statement.setInt(1, dateId);
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
