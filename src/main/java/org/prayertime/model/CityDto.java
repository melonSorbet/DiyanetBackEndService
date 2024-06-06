package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDto {
    int id;
    @JsonProperty("code")
    String cityCode;
    @JsonProperty("name")
    String cityName;
}
