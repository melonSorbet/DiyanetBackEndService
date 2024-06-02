package org.prayertime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.prayertime.config.AppConfig;
import org.prayertime.model.ApiAccess;
import org.prayertime.model.Data;
import org.prayertime.model.DayDto;
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

    public final AppConfig config;

    @Autowired
    public DiyanetApiController(AppConfig config) {
        this.config = config;
    }
    @PostConstruct
    public void printemail(){
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

        Data<ApiAccess> stringData = jsonToJava.readValue(jsonResponse, new TypeReference<>() {});

        return stringData.getData().getAccessToken();
    }

    public String getRequests(String uri, String accessToken){
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

        Data<DayDto[]> data = jsonToJavaMapper.readValue(httpBody, new TypeReference<>() {});
        return data.getData();
    }
    public DayDto getCurrentDay(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Daily/11002", accessToken);

        Data<DayDto[]> data = jsonToJavaMapper.readValue(httpBody, new TypeReference<>() {});
        return data.getData()[0];
    }


    public static void main(String[] arg) throws IOException, URISyntaxException, InterruptedException {
        DiyanetApiController diyanetApi = new DiyanetApiController(new AppConfig());
        /*String accessToken = diyanetApi.getAccessToken();
        String body = Arrays.toString(diyanetApi.getNextMonth(accessToken));
        System.out.println(body);*/

        System.out.println(diyanetApi.config.getPassword());
    }
}
