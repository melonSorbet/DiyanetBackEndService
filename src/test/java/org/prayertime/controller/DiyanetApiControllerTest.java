package org.prayertime.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prayertime.config.AppConfig;
import org.prayertime.model.DayDto;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class DiyanetApiControllerTest {
    public static final String ACCESS_TOKEN = "access_token";
    private DiyanetApiController diyanetApiController;
    private ObjectMapper objectMapper;
    String mockAccessToken = ACCESS_TOKEN;
    private HttpClient httpClient;
    private AppConfig appConfig;

    @BeforeEach
    public void setUp() {
        httpClient = mock(HttpClient.class);
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        diyanetApiController = new DiyanetApiController(appConfig, httpClient);
    }

    @Test
    void getAccessToken() {
    }

    @Test
    void makeGetRequest() {
    }

    @Test
    public void testGetNextMonth() throws IOException, InterruptedException {
        String mockHttpBody =
                "{\n" +
                        "  \"data\": [\n" +
                        "    {\n" +
                        "      \"shapeMoonUrl\": \"http://namazvakti.diyanet.gov.tr/images/r5.gif\",\n" +
                        "      \"fajr\": \"06:11\",\n" +
                        "      \"sunrise\": \"07:42\",\n" +
                        "      \"dhuhr\": \"12:38\",\n" +
                        "      \"asr\": \"15:01\",\n" +
                        "      \"maghrib\": \"17:23\",\n" +
                        "      \"isha\": \"18:49\",\n" +
                        "      \"astronomicalSunset\": \"17:16\",\n" +
                        "      \"astronomicalSunrise\": \"07:49\",\n" +
                        "      \"hijriDateShort\": \"5.5.1444\",\n" +
                        "      \"hijriDateShortIso8601\": null,\n" +
                        "      \"hijriDateLong\": \"5 Cemaziyelevvel 1444\",\n" +
                        "      \"hijriDateLongIso8601\": null,\n" +
                        "      \"qiblaTime\": \"11:31\",\n" +
                        "      \"gregorianDateShort\": \"29.11.2022\",\n" +
                        "      \"gregorianDateShortIso8601\": \"29.11.2022\",\n" +
                        "      \"gregorianDateLong\": \"29 Kasım 2022 Salı\",\n" +
                        "      \"gregorianDateLongIso8601\": \"2022-11-29T00:00:00.0000000+03:00\",\n" +
                        "      \"greenwichMeanTimeZone\": 3\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": null\n" +
                        "}";
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);
        Mockito.when(mockHttpResponse.body()).thenReturn(mockHttpBody);

        Mockito.when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandlers.ofString().getClass()))).thenReturn(mockHttpResponse);
        Mockito.when(diyanetApiController.makeGetRequest("https://hallo.com", "")).thenReturn(mockHttpBody);
        DayDto dayDto = new DayDto(29112022, 611, 1238, 1501, 1723, 1849, 742);
        System.out.println(dayDto.equals(diyanetApiController.getCurrentDay("hallo")));
    }

    @Test
    void getNextWeek() {
    }

    @Test
    void getCurrentDay() {
    }

    @Test
    void getDailyContent() {
    }

    @Test
    void getCountries() {
    }

    @Test
    void getAllCitiesFromCountry() {
    }

    @Test
    void getCities() {
    }
}