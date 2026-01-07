package org.prayertime.controller;

import org.prayertime.model.CityDto;
import org.prayertime.model.CountryDto;
import org.prayertime.model.DayDto;
import org.prayertime.service.ApiRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class ApiController {
    ApiRequestService apiRequestService;

    public ApiController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @GetMapping("/Countries")
    public CountryDto[] getCountry() throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getCountries();
    }

    @GetMapping("/States")
    public CityDto[] getStatesFromCountry(@RequestParam int countryId) throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getStates(countryId);
    }

    @GetMapping("/Cities")
    public CityDto[] getCitiesFromState(@RequestParam int stateId) throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getCities(stateId);
    }

    @GetMapping("/PrayerTimes/weekly")
    public DayDto[] getWeeklyPrayerTimes(@RequestParam int cityId) throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getWeeklyPrayerTimes(cityId);
    }

    @GetMapping("/PrayerTimes/monthly")
    public DayDto[] getMonthlyPrayerTimes(@RequestParam int cityId) throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getMonthlyPrayerTimes(cityId);
    }

    @GetMapping("/PrayerTimes/daily")
    public DayDto getDailyPrayerTimes(@RequestParam int cityId) throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getDailyPrayerTime(cityId);
    }

    @GetMapping("/PrayerTimes/betweentwodates")
    public DayDto[] getPrayerTimesBetweenTwoDates(@RequestParam int cityId, @RequestParam int firstDate, @RequestParam int lastDate) throws IOException, URISyntaxException, InterruptedException {
        return apiRequestService.getMonthlyPrayerTimes(cityId);
    }
}
