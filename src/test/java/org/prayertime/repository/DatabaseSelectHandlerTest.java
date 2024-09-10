package org.prayertime.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prayertime.config.DataSourceConfig;
import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DailyContentDto;
import org.prayertime.model.DayDto;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseSelectHandlerTest {
    DataSource dataSource = DataSourceConfig.createDataSource(30000, 10000, 250, 1, 1,
            "org.sqlite.JDBC", "", "", "jdbc:sqlite::memory:");
    DatabaseInitializer databaseInitializer = new DatabaseInitializer(dataSource);
    DatabaseInsertHandler databaseInsertHandler = new DatabaseInsertHandler(dataSource);
    DatabaseSelectHandler databaseSelectHandler = new DatabaseSelectHandler(dataSource);

    @BeforeEach
    public void startup() throws ClassNotFoundException {
        databaseInitializer.initializeDatabase();
    }

    @Test
    void selectDaysFromCityBetweenTimes() {
        DayDto[] dayDtos = {
                new DayDto(2042004, 332, 1313, 1736, 2131, 2248, 458),
                new DayDto(2142004, 334, 1315, 1738, 2133, 2250, 460),
                new DayDto(2242004, 336, 1317, 1740, 2135, 2252, 502),
        };
        databaseInsertHandler.insertDays(dayDtos, 113);
        var result = databaseSelectHandler.selectDaysFromCityBetweenTimes(113, 2042004, 2142004);
        DayDto[] expected = {
                new DayDto(2042004, 332, 1313, 1736, 2131, 2248, 458),
                new DayDto(2142004, 334, 1315, 1738, 2133, 2250, 460),
        };

        assertArrayEquals(expected, result.toArray());
    }

    @Test
    void selectCitiesFromCountry() {
        CityDto[] cityDtos = {
                new CityDto(11001, "", "Berlin"),
                new CityDto(11002, "", "Dortmund"),
                new CityDto(11003, "", "Stuttgart"),
        };
        databaseInsertHandler.insertCity(cityDtos, 112);
        List<CityDto> expectedCities = databaseSelectHandler.selectCitiesFromCountry(112);
        assertArrayEquals(cityDtos, expectedCities.toArray());
    }

    @Test
    void selectCountries() {
        CountryDto[] countryDtos = {
                new CountryDto(1, "", "Germany"),
                new CountryDto(2, "", "England"),
                new CountryDto(3, "", "France")
        };
        databaseInsertHandler.insertCountries(countryDtos);
        List<CountryDto> countries = databaseSelectHandler.selectCountries();
        assertArrayEquals(countryDtos, countries.toArray());
    }

    @Test
    void selectDailyContentDto() {
        DailyContentDto dailyContentDto = new DailyContentDto(1, 2042004, "verse", "verseSource", "hadith", "hadithSource", "pray", null);
        databaseInsertHandler.insertDailyContent(dailyContentDto);
        DailyContentDto test = databaseSelectHandler.selectDailyContentDto(2042004);
        assertEquals(dailyContentDto, test);
    }
}