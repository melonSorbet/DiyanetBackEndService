package org.prayertime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.prayertime.config.AppConfig;
import org.prayertime.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class DiyanetApiController {
    private final AppConfig appConfig;
    private final HttpClient httpClient;

    @Autowired
    public DiyanetApiController(AppConfig config, HttpClient httpClient) {
        this.appConfig = config;
        this.httpClient = httpClient;
    }

    public String getAccessToken() throws IOException, InterruptedException, URISyntaxException {
        String data = "{\n" +
                "\"email\": \"" + appConfig.getEmail() + "\",\n" +
                "\"password\": \"" + appConfig.getPassword() + "\"\n" +
                "}";
        HttpRequest auth = HttpRequest.newBuilder().uri(new URI("https://awqatsalah.diyanet.gov.tr/Auth/Login")).
                POST(HttpRequest.BodyPublishers.ofString(data)).header("Content-Type", "application/json").build();

        HttpResponse<String> httpResponse = httpClient.send(auth, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = httpResponse.body();

        DataDto<ApiAccess> stringDataDto = new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {
        });

        return stringDataDto.getData().getAccessToken();
    }

    String makeGetRequest(String uri, String accessToken) {
        try {
            HttpRequest auth = HttpRequest.newBuilder().uri(new URI(uri)).GET().
                    header("Authorization", "Bearer " + accessToken).
                    header("Content-Type", "application/json").build();
            HttpResponse<String> httpResponse = httpClient.send(auth, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DayDto[] getNextMonth(String accessToken) throws JsonProcessingException {
        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Monthly/11002", accessToken);
        System.out.println("Weekly Response: " + httpBody); // Log the response
        DataDto<DayDto[]> dataDto = new ObjectMapper().readValue(httpBody, new TypeReference<>() {
        });
        return dataDto.getData();
    }

    public DayDto[] getNextWeek(String accessToken) throws JsonProcessingException {
        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Weekly/11002", accessToken);
        System.out.println("Weekly Response: " + httpBody); // Log the response
        DataDto<DayDto[]> dataDto = new ObjectMapper().readValue(httpBody, new TypeReference<>() {
        });
        return dataDto.getData();
    }

    public DayDto getCurrentDay(String accessToken, int cityId) throws JsonProcessingException {

        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Daily/" + cityId, accessToken);
        //build in error code so we can see that quota is reached
        DataDto<DayDto[]> dataDto = new JsonMapper().readValue(httpBody, new TypeReference<>() {
        });
        return dataDto.getData()[0];
    }

    protected DailyContentDto getDailyContent(String accessToken) throws JsonProcessingException {
        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/DailyContent", accessToken);

        DataDto<DailyContentDto> dataDto = new ObjectMapper().readValue(httpBody, new TypeReference<DataDto<DailyContentDto>>() {
        });
        return dataDto.getData();
    }

    public CountryDto[] getCountries(String accessToken) throws JsonProcessingException {
        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/Place/Countries", accessToken);

        DataDto<CountryDto[]> dataDto = new ObjectMapper().readValue(httpBody, new TypeReference<DataDto<CountryDto[]>>() {
        });
        return dataDto.getData();
    }

    public CityDto[] getAllStatesFromCountry(String accessToken, Integer countryId) throws JsonProcessingException {
        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/Place/States/" + countryId, accessToken);

        DataDto<CityDto[]> dataDto = new ObjectMapper().readValue(httpBody, new TypeReference<DataDto<CityDto[]>>() {
        });
        return dataDto.getData();
    }

    public CityDto[] getAllCitiesFromState(String accessToken, Integer stateId) throws JsonProcessingException {
        String httpBody = makeGetRequest("https://awqatsalah.diyanet.gov.tr/api/Place/Cities/" + stateId, accessToken);

        DataDto<CityDto[]> dataDto = new ObjectMapper().readValue(httpBody, new TypeReference<DataDto<CityDto[]>>() {
        });
        return dataDto.getData();
    }
}
