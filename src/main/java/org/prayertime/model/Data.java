package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data<Dto>{
    private int code;
    private String status;
    @JsonProperty("data")
    private Dto data;

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
    public Dto getData() {
        return data;
    }
}
