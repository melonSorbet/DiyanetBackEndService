package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDto {
    private int gregorianDateShort;
    private int fajr;
    private int dhuhr;
    private int asr;
    private int maghrib;
    private int isha;
    private int sunrise;

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

