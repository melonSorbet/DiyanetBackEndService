package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDto {
    private String gregorianDateShort;
    private String fajr;
    private String dhuhr;
    private String asr;
    private String maghrib;
    private String isha;
    private String sunrise;

    @Override
    public String toString() {
        return "DayDto{" +
                "gregorianDateShort='" + gregorianDateShort + '\'' +
                ", fajr='" + fajr + '\'' +
                ", dhuhr='" + dhuhr + '\'' +
                ", asr='" + asr + '\'' +
                ", maghrib='" + maghrib + '\'' +
                ", isha='" + isha + '\'' +
                ", sunrise='" + sunrise + '\'' +
                '}';
    }

}
