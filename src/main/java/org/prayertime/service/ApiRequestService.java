package org.prayertime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.prayertime.controller.DiyanetApiController;
import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DayDto;
import org.prayertime.repository.DatabaseInsertHandler;
import org.prayertime.repository.DatabaseSelectHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ApiRequestService {
    private final DiyanetApiController diyanetApiController;
    private final DatabaseInsertHandler databaseInsertHandler;
    private final DatabaseSelectHandler databaseSelectHandler;

    ApiRequestService(DiyanetApiController diyanetApiController, DatabaseSelectHandler databaseSelectHandler, DatabaseInsertHandler databaseInsertHandler, ObjectMapper dateToIntMapper) {
        this.diyanetApiController = diyanetApiController;
        this.databaseInsertHandler = databaseInsertHandler;
        this.databaseSelectHandler = databaseSelectHandler;
    }

    public DayDto[] getWeeklyPrayerTimes(int cityId) throws IOException, URISyntaxException, InterruptedException {
        LocalDate date = java.time.LocalDate.now();
        LocalDate dateEnd = date.plusDays(7);
       
        String accessToken = diyanetApiController.getAccessToken();
        DayDto[] dayDtos = diyanetApiController.getNextWeek(accessToken);

        return dayDtos;
    }

    public DayDto[] getMonthlyPrayerTimes(int cityId) throws IOException, URISyntaxException, InterruptedException {
        LocalDate date = java.time.LocalDate.now();
        LocalDate dateEnd = date.plusDays(30);

        String accessToken = diyanetApiController.getAccessToken();
        DayDto[] dayDtos = diyanetApiController.getNextMonth(accessToken);

        return dayDtos;
    }

    public DayDto getDailyPrayerTime(int cityId) throws IOException, URISyntaxException, InterruptedException {
        LocalDate date = java.time.LocalDate.now();
        LocalDate dateEnd = date.plusDays(1);

        String accessToken = diyanetApiController.getAccessToken();
        return diyanetApiController.getCurrentDay(accessToken, cityId);
    }

    public CityDto[] getStates(int countryId) throws IOException, URISyntaxException, InterruptedException {

        List<CityDto> cityDtos = databaseSelectHandler.selectCitiesFromCountry(countryId);

        String accessToken = diyanetApiController.getAccessToken();
        var cities = diyanetApiController.getAllStatesFromCountry(accessToken, countryId);
        return cities;
    }

    public CityDto[] getCities(int statesId) throws IOException, URISyntaxException, InterruptedException {

        List<CityDto> cityDtos = databaseSelectHandler.selectCitiesFromCountry(statesId);

        if (!cityDtos.isEmpty()) {
            return cityDtos.stream()
                    .map(obj -> (CityDto) obj)
                    .toArray(CityDto[]::new);
        }
        String accessToken = diyanetApiController.getAccessToken();
        var cities = diyanetApiController.getAllCitiesFromState(accessToken, statesId);
        return cities;
    }

    public CountryDto[] getCountries() throws IOException, URISyntaxException, InterruptedException {

        String accessToken = diyanetApiController.getAccessToken();

        return diyanetApiController.getCountries(accessToken);
    }

}
