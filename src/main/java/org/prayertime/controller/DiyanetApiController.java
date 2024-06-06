package org.prayertime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
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
import java.util.Arrays;

@Controller
public class DiyanetApiController {
    public final AppConfig config;

    @Autowired
    public DiyanetApiController(AppConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void printemail() {
        System.out.println("Password: " + config.getPassword());
    }

    public String getAccessToken() throws IOException, InterruptedException, URISyntaxException {
        String data = "{\n" +
                "\"email\": \"" + config.getEmail() + "\",\n" +
                "\"password\": \"" + config.getPassword() + "\"\n" +
                "}";
        HttpRequest auth = HttpRequest.newBuilder().uri(new URI("https://awqatsalah.diyanet.gov.tr/Auth/Login")).
                POST(HttpRequest.BodyPublishers.ofString(data)).header("Content-Type", "application/json").build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> httpResponse = httpClient.send(auth, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = httpResponse.body();
        System.out.println(jsonResponse);
        var jsonToJava = new ObjectMapper();

        DataDto<ApiAccess> stringDataDto = jsonToJava.readValue(jsonResponse, new TypeReference<>() {
        });

        return stringDataDto.getData().getAccessToken();
    }

    public String getRequests(String uri, String accessToken) {
        try {
            HttpRequest auth = HttpRequest.newBuilder().uri(new URI(uri)).GET().
                    header("Authorization", "Bearer " + accessToken).
                    header("Content-Type", "application/json").build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = httpClient.send(auth, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DayDto[] getNextMonth(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Monthly/11002", accessToken);

        DataDto<DayDto[]> dataDto = jsonToJavaMapper.readValue(httpBody, new TypeReference<>() {
        });
        return dataDto.getData();
    }

    public DayDto getCurrentDay(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Daily/11002", accessToken);

        DataDto<DayDto[]> dataDto = jsonToJavaMapper.readValue(httpBody, new TypeReference<>() {
        });
        return dataDto.getData()[0];
    }

    public DailyContentDto getDailyContent(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/DailyContent", accessToken);

        DataDto<DailyContentDto> dataDto = jsonToJavaMapper.readValue(httpBody, new TypeReference<DataDto<DailyContentDto>>() {
        });
        return dataDto.getData();
    }

    public CountryDto[] getCountries(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/Place/Countries", accessToken);

        DataDto<CountryDto[]> dataDto = jsonToJavaMapper.readValue(httpBody, new TypeReference<DataDto<CountryDto[]>>() {
        });
        return dataDto.getData();
    }

    public CityDto[] getAllCitiesFromCountry(String accessToken, Integer CountryId) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/Place/States/" + CountryId, accessToken);

        DataDto<CityDto[]> dataDto = jsonToJavaMapper.readValue(httpBody, new TypeReference<DataDto<CityDto[]>>() {
        });
        return dataDto.getData();
    }

    public CountryDto[] getCities(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/Place/Countries", accessToken);

        DataDto<CountryDto[]> dataDto = jsonToJavaMapper.readValue(httpBody, new TypeReference<DataDto<CountryDto[]>>() {
        });
        return dataDto.getData();
    }

    public static void main(String[] arg) throws IOException, URISyntaxException, InterruptedException {
        DiyanetApiController diyanetApi = new DiyanetApiController(new AppConfig());
        String accessToken = diyanetApi.getAccessToken();
        String body = Arrays.toString(diyanetApi.getNextMonth(accessToken));
        System.out.println(Arrays.toString(diyanetApi.getAllCitiesFromCountry(accessToken, 13)));
    }
}
