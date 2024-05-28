package org.prayertimes.java.com.example.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimingsDto {

    @JsonProperty("Fajr")
    private String fajr;

    @JsonProperty("Dhuhr")
    private String dhuhr;

    @JsonProperty("Asr")
    private String asr;


    public String getFajr() {
        return fajr;
    }
    public String getDhuhr(){
        return dhuhr;
    }

    public String getAsr() {
        return asr;
    }
}
