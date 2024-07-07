package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.prayertime.service.DateToIntDeserializer;
import org.prayertime.service.TimeToIntDeserializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDto {
    @JsonDeserialize(using = DateToIntDeserializer.class)
    private int gregorianDateShort;
    @JsonDeserialize(using = TimeToIntDeserializer.class)
    private int fajr;
    @JsonDeserialize(using = TimeToIntDeserializer.class)
    private int dhuhr;
    @JsonDeserialize(using = TimeToIntDeserializer.class)
    private int asr;
    @JsonDeserialize(using = TimeToIntDeserializer.class)
    private int maghrib;
    @JsonDeserialize(using = TimeToIntDeserializer.class)

    private int isha;
    @JsonDeserialize(using = TimeToIntDeserializer.class)
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

