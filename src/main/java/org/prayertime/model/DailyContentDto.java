package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyContentDto {
    int id;
    int dayOfYear;
    String verse;
    String verseSource;
    String hadith;
    String hadithSource;
    String prayer;
    String prayerSource;
}
