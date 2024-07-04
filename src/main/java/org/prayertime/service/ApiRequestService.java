package org.prayertime.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.prayertime.config.AppConfig;
import org.prayertime.controller.DiyanetApiController;
import org.prayertime.model.DayDto;
import org.prayertime.repository.DatabaseInsertHandler;
import org.prayertime.repository.DatabaseSelectHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiRequestService {
    private AppConfig appConfig;
    private final DiyanetApiController diyanetApiController;
    private final DatabaseInsertHandler databaseInsertHandler;
    private final DatabaseSelectHandler databaseSelectHandler;
    private String accessToken;

    ApiRequestService(AppConfig appConfig, DiyanetApiController diyanetApiController, DatabaseSelectHandler databaseSelectHandler, DatabaseInsertHandler databaseInsertHandler) {
        this.appConfig = appConfig;
        this.diyanetApiController = diyanetApiController;
        this.databaseInsertHandler = databaseInsertHandler;
        this.databaseSelectHandler = databaseSelectHandler;
    }

    public DayDto[] getWeeklyPrayerTimes(int cityId, int firstDay, int lastDay) throws JsonProcessingException {
        if (firstDay > lastDay) {
            return null;
        }
        List<DayDto> dayDto = databaseSelectHandler.selectDaysFromCityBetweenTimes(cityId, firstDay, lastDay);

        if (!dayDto.isEmpty()) {
            return dayDto.stream()
                    .map(obj -> (DayDto) obj) // Casting each Object to DayDto
                    .toArray(DayDto[]::new);
        }
        String accessToken = getAccessToken();
        return diyanetApiController.getNextWeek(accessToken);
    }

    public DayDto[] getMonthlyPrayerTimes() {
        return null;
    }

    public DayDto[] getPrayerTimeOnSpecificDay() {
        return null;

    }

    public String getAccessToken() {
        return "";
    }

}
