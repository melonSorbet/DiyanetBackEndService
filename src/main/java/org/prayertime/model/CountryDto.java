package org.prayertime.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto {
    int id;
    @JsonProperty("code")
    String countryCode;
    @JsonProperty("name")
    String countryName;
}
