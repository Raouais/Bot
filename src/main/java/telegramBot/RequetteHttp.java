package telegramBot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequetteHttp {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(getRequette()))
            .build();

    HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());

    RequetteHttp() throws IOException, InterruptedException {
    }

    public String getRequette(){
        return "https://api.polygonscan.com/api?module=account&action=txlist&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&startblock=0&endblock=99999999&page=1&offset=100&sort=asc&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP";
    }

    public String retourneLeSoldessDeLaTransaction(int position, String element){
        String test;
        String jsonData = response.body();
        JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
        JSONObject site = (JSONObject)(((JSONArray)forecast.get("result")).get(position));
        test = (String) site.get(element);
        return test;
    }

    public String recupererPrix(String symbol){
        Prix prix = new Prix();
        return prix.execution(symbol);
    }

    Double recupererSoldeCosmos(String symbol){
        String data = recupererPrix(symbol);
        JSONObject forecast = (JSONObject) JSONValue.parse(data);
        JSONObject resultat = (JSONObject) forecast.get("data");
        JSONObject forecast2 = (JSONObject) JSONValue.parse(String.valueOf(resultat));

        JSONObject resultat2 = (JSONObject) forecast2.get(symbol);
        JSONObject forecast3 = (JSONObject) JSONValue.parse(String.valueOf(resultat2));

        JSONObject resultat3 = (JSONObject) forecast3.get("quote");
        JSONObject forecast4 = (JSONObject) JSONValue.parse(String.valueOf(resultat3));

        JSONObject resultat4 = (JSONObject) forecast4.get("EUR");
        JSONObject forecast5 = (JSONObject) JSONValue.parse(String.valueOf(resultat4));

        return (double) forecast5.get("price");
    }

    // prévoir argument symbol
    public Double calculMontantAcquis(double montantMatic){
        Sql sql = new Sql();

        double res = (sql.recupererPrixMatic() / sql.recupererPrixErowan());

        double calcul = montantMatic * res;

        double swapFEE = calcul - (calcul * 0.6 /100);

        return swapFEE - (swapFEE * 0.4 /100);
    }


    public double calculMontantAcquisStaking(double montantMatic) {
        Date date = new Date();
        Sql sql = new Sql();
        double calculMontantAcquisStaking;

        double c = calculMontantAcquis(montantMatic);
        //System.out.println("-------------------> c" + c);
        double apr;
        apr = sql.recupererAprReel();
        double r = apr / 100;
        //System.out.println(r);
        double t = date.calculDate() / 365.;
        System.out.println("timing =" + t);
        calculMontantAcquisStaking = c * t * r;
        //System.out.println("--------------------------------> stakingMontant" + calculMontantAcquisStaking);
        return calculMontantAcquisStaking;
    }

    public double calculMontantAcquisStaking(double montantMatic, int nbJours) {
        Sql sql = new Sql();
        double calculMontantAcquisStaking;
        double c = calculMontantAcquis(montantMatic);
        double apr;

        apr = sql.recupererAprReel();

        double r = apr / 100;

        double t = nbJours / 365.;
        System.out.println("timing =" + t);

        calculMontantAcquisStaking = c * r * t;

        return calculMontantAcquisStaking;
    }

    double recupererSoldeMatic(){
        double totalMatic = 0;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.polygonscan.com/api?module=account&action=balance&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP"))
                .build();
        {
            try {
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());

                String jsonData1 = response.body();
                JSONObject forecast = (JSONObject) JSONValue.parse(jsonData1);
                String resultat = (String) forecast.get("result");
                double resultatMatic = Double.parseDouble(resultat);
                double exposantMoin18 = Math.pow(10.0,-18.0);
                totalMatic = resultatMatic * exposantMoin18;
                return totalMatic;

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return totalMatic;
    }

    public String vaChercherResultSurPolygonscan(int position){
        String test = "vide";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getRequette()))
                .build();
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            //System.out.println("voici le corps de vaChercher: " + response.body());
            String jsonData = response.body();
            JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
            JSONObject site = (JSONObject)(((JSONArray)forecast.get("result")).get(position));
            test = (String) site.get("timeStamp");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return test;
    }

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
            //System.out.println("voici le corps: " + response.body());
            String jsonData = response.body();
            JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
            String test = (String) forecast.get("status");
            test2 = Integer.parseInt(test);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return test2;
    }
}


/*
https://api.polygonscan.com/api?module=account&action=balance&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP
 */
/*
https://api.polygonscan.com/api?module=account&action=txlist&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&startblock=0&endblock=99999999&page=1&offset=10&sort=asc&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP
 jouer avec les pages
 */

/*
RequetteHttp(String URL) throws IOException, InterruptedException {
    this.URL = URL;
}
 */
//String URL;