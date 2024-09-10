package org.prayertime.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prayertime.config.DataSourceConfig;
import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DailyContentDto;
import org.prayertime.model.DayDto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseInsertHandlerTest {
    DataSource dataSource = DataSourceConfig.createDataSource(30000, 10000, 250, 1, 1,
            "org.sqlite.JDBC", "", "", "jdbc:sqlite::memory:");
    DatabaseInitializer databaseInitializer = new DatabaseInitializer(dataSource);
    DatabaseInsertHandler databaseInsertHandler = new DatabaseInsertHandler(dataSource);

    @BeforeEach
    void setUp() throws ClassNotFoundException {
        databaseInitializer.initializeDatabase();
    }

    @Test
    void insertCity() {
        CityDto[] cities = {
                new CityDto(1, "NEWYORK", "New York"),
                new CityDto(2, "LOSANGELES", "Los Angeles"),
                new CityDto(3, "CHICAGO", "Chicago")
        };
        databaseInsertHandler.insertCity(cities, 123);
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM City")) {

            int count = 0;
            while (rs.next()) {
                count++;
                int cityId = rs.getInt("cityId");
                String cityName = rs.getString("cityName");
                int retrievedCountryId = rs.getInt("countryId");

                assertTrue(cityId == 1 || cityId == 2 || cityId == 3);
                assertTrue(cityName.equals("New York") || cityName.equals("Los Angeles") || cityName.equals("Chicago"));
                assertEquals(123, retrievedCountryId);
            }

            assertEquals(cities.length, count, "Number of inserted cities should match the length of the input array");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insertCountries() {
        CountryDto[] countries = {
                new CountryDto(1, "GERMANY", "Germany"),
                new CountryDto(2, "ENGLAND", "England"),
                new CountryDto(3, "FRANCE", "France")
        };
        databaseInsertHandler.insertCountries(countries);
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Country")) {

            int count = 0;
            while (rs.next()) {
                count++;
                int cityId = rs.getInt("countryId");
                String cityName = rs.getString("countryName");

                assertTrue(cityId == 1 || cityId == 2 || cityId == 3);
                assertTrue(cityName.equals("Germany") || cityName.equals("England") || cityName.equals("France"));
            }

            assertEquals(countries.length, count, "Number of inserted cities should match the length of the input array");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insertDailyContent() {
        DailyContentDto dailyContentDto = new DailyContentDto(0, 2042004, "verse", "verseSource", "hadith", "hadithSource", "pray", "prayerSource");

        databaseInsertHandler.insertDailyContent(dailyContentDto);
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM DailyContent")) {

            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                int dateid = rs.getInt("dateid");
                String verse = rs.getString("verse");
                String verseSource = rs.getString("verseSource");
                String hadith = rs.getString("hadith");
                String hadithSource = rs.getString("hadithSource");
                String prayer = rs.getString("pray");

                assertEquals(1, id);
                assertEquals(2042004, dateid);
                assertEquals(verse, "verse");
                assertEquals(verseSource, "verseSource");
                assertEquals(hadith, "hadith");
                assertEquals(hadithSource, "hadithSource");
                assertEquals(prayer, "pray");

            }

            assertEquals(1, count, "Number of inserted cities should match the length of the input array");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insertDayDto() {
        DayDto[] dayDtos = {
                new DayDto(2042004, 332, 1313, 1736, 2131, 2248, 458),
                new DayDto(2142004, 334, 1315, 1738, 2133, 2250, 460),
                new DayDto(2242004, 336, 1317, 1740, 2135, 2252, 502),
        };
        databaseInsertHandler.insertDays(dayDtos, 110011);
        try (Connection conn = dataSource.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Day")) {

            int count = 0;
            while (rs.next()) {
                count++;
                int dateid = rs.getInt("dateid");
                int cityId = rs.getInt("cityId");
                int fajr = rs.getInt("fajr");
                int asr = rs.getInt("asr");
                int dhuhr = rs.getInt("dhuhr");
                int isha = rs.getInt("isha");
                int maghrib = rs.getInt("maghrib");
                int sunrise = rs.getInt("sunrise");

                assertEquals(110011, cityId);
                assertTrue(dateid == 2042004 || dateid == 2142004 || dateid == 2242004);
                assertTrue(fajr == 332 || fajr == 334 || fajr == 336);
                assertTrue(dhuhr == 1313 || dhuhr == 1315 || dhuhr == 1317);
                assertTrue(asr == 1736 || asr == 1738 || asr == 1740);
                assertTrue(isha == 2248 || isha == 2250 || isha == 2252);
                assertTrue(maghrib == 2131 || maghrib == 2133 || maghrib == 2135);
                assertTrue(sunrise == 458 || sunrise == 460 || sunrise == 502);
            }

            assertEquals(dayDtos.length, count, "Number of inserted cities should match the length of the input array");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}