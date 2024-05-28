package example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class diyanetapi {
    private static final String YOUR_EMAIL = "";
    private static final String YOUR_PASSWORD = "";

    public String getAccessToken() throws IOException, InterruptedException, URISyntaxException {
        String data = "{\n" +
                "\"email\": \"" + YOUR_EMAIL + "\",\n" +
                "\"password\": \"" + YOUR_PASSWORD + "\"\n" +
                "}";
        HttpRequest auth = HttpRequest.newBuilder().uri(new URI("https://awqatsalah.diyanet.gov.tr/Auth/Login")).
                POST(HttpRequest.BodyPublishers.ofString(data)).header("Content-Type", "application/json").build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> httpResponse = httpClient.send(auth, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = httpResponse.body();
        System.out.println(jsonResponse);
        var jsonToJava = new ObjectMapper();

        org.prayertimes.java.com.example.dtos.Data<org.prayertimes.java.com.example.dtos.ApiAccess> stringData = jsonToJava.readValue(jsonResponse, new TypeReference<org.prayertimes.java.com.example.dtos.Data<org.prayertimes.java.com.example.dtos.ApiAccess>>() {});

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
    public org.prayertimes.java.com.example.dtos.DayDto[] getNextMonth(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Monthly/11002", accessToken);

        org.prayertimes.java.com.example.dtos.Data<org.prayertimes.java.com.example.dtos.DayDto[]> data = jsonToJavaMapper.readValue(httpBody, new TypeReference<>() {});
        return data.getData();
    }
    public org.prayertimes.java.com.example.dtos.DayDto getCurrentDay(String accessToken) throws JsonProcessingException {
        var jsonToJavaMapper = new ObjectMapper();
        String httpBody = getRequests("https://awqatsalah.diyanet.gov.tr/api/PrayerTime/Daily/11002", accessToken);

        org.prayertimes.java.com.example.dtos.Data<org.prayertimes.java.com.example.dtos.DayDto[]> data = jsonToJavaMapper.readValue(httpBody, new TypeReference<>() {});
        return data.getData()[0];
    }


    public static void main(String[] arg) throws IOException, URISyntaxException, InterruptedException {
        diyanetapi diyanetApi = new diyanetapi();
        String accessToken = diyanetApi.getAccessToken();
        String body = Arrays.toString(diyanetApi.getNextMonth(accessToken));
        System.out.println(body);
    }
}
