package org.prayertime.repository;

import org.prayertime.config.AppConfig;
import org.prayertime.model.DayDto;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@Repository
public class DatabaseInsertHandler {

    private AppConfig appConfig;

    public DatabaseInsertHandler(AppConfig appConfig){
        this.appConfig = appConfig;
    }
    public static void insertDays(List<DayDto> dayDtos){
        try(Connection conn = DriverManager.getConnection())
    }
    public static void insertMonths(){

    }
    public static void insertCities(){

    }
    public static void insertEid(){

    }
    public static void insertDailyContent(){

    }
    public static void insertCountries(){

    }
    public static void insertWeekly(){

    }
    public static void insertRamadan(){

    }


}
