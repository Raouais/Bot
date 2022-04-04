import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiPolygonscan {
    private String requette = "https://api.polygonscan.com/api?module=account&action=txlist&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&startblock=0&endblock=99999999&page=1&offset=100&sort=asc&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP";

    public String getRequette(){
        return requette;
    }

    public void setRequette(String laRequette){
        requette = laRequette;
    }

    HttpClient client = HttpClient.newHttpClient();


    public int retourneLeStatusDeLaTransaction(int position){
        int test2 = 0;
        String positionString = String.valueOf(position);
        String requettePlusPosition = "https://api.polygonscan.com/api?module=account&action=txlist&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&startblock=0&endblock=99999999&page=" + positionString + "&offset=1&sort=asc&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requettePlusPosition))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("voici le corps: " + response.body());
            String jsonData = response.body();
            JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
            String test = (String) forecast.get("status");
            test2 = Integer.parseInt(test);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return test2;
    }

    public String vaChercherResultSurPolygonscan(int position){
        String test = "vide";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getRequette()))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println("voici le corps de vaChercher: " + response.body());
            String jsonData = response.body();
            JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
            JSONObject site = (JSONObject)(((JSONArray)forecast.get("result")).get(position));
            test = (String) site.get("timeStamp");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return test;
    }
}
