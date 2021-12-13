package POO;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;

public class CallOfDuty {

double scoreParMinute = 0;
int partieChoisieDebut = 0;
int partieChoisieFin = 0;

    public String requestAPI() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://call-of-duty-modern-warfare.p.rapidapi.com/multiplayer-matches/Garanarogas%25232561/battle")
                .get()
                .addHeader("x-rapidapi-key", "b90a1daebemshb43c013454d216cp1a2d86jsn0c387bddeec4")
                .addHeader("x-rapidapi-host", "call-of-duty-modern-warfare.p.rapidapi.com")
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        return jsonData;
    }

    public JSONObject manipulationJSON() throws IOException {
        JSONObject forecast = (JSONObject) JSONValue.parse(requestAPI());

        JSONObject matches = (JSONObject) forecast.get("summary");
        System.out.println(matches.get("all"));
        JSONObject kill = (JSONObject) matches.get("all");
        System.out.println(kill.get("scorePerMinute"));
        scoreParMinute = (double) kill.get("scorePerMinute");
        return forecast;
    }

    public int calculDuChoixPartie(int partieChoisie){
            partieChoisieFin = partieChoisie;
            partieChoisieDebut = partieChoisie;
            partieChoisieDebut--;
            System.out.println(partieChoisieDebut);
            System.out.println(partieChoisieFin);
            int finalPartieChoisie = partieChoisieFin;
            return finalPartieChoisie;
    }

    public double choixDeLaPartie() throws IOException {
        for (int i = partieChoisieDebut; i < partieChoisieFin; i++){
            JSONObject site = (JSONObject)(((JSONArray)manipulationJSON().get("matches")).get(i));
            JSONObject testo = (JSONObject)site.get("playerStats");
            JSONObject testa = (JSONObject)site.get("weaponStats");
            System.out.println(site.get("map"));
            System.out.println(testa + " " + "fusille utilisé");
            System.out.println(testo.get("kills") + " " + "kills");
            System.out.println(testo.get("deaths")+ " " + "mort");
            System.out.println(testo.get("accuracy")+ " " + "précision");
            System.out.println(testo.get("scorePerMinute")+ " " + "Score par minute");
            scoreParMinute = (double) testo.get("scorePerMinute");
        }
        return scoreParMinute;
    }

    public String toString(){
        try {
            return "la partie " + calculDuChoixPartie(19) + " "  + choixDeLaPartie();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "erreur";
    }
}
