/*
    import org.telegram.telegrambots.meta.TelegramBotsApi;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
    import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;




    public class Main {
        public static void main(String[] args) {
            try {
                //TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                //telegramBotsApi.registerBot(new MyAmazingBot());

                TelegramBotsApi botsApi2 = new TelegramBotsApi(DefaultBotSession.class);
                botsApi2.registerBot(new ReplyFlowBot());

                //TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                //botsApi.registerBot(new MultipleCommande());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://call-of-duty-modern-warfare.p.rapidapi.com/multiplayer-matches/Garanarogas%25232561/battle")
                            .get()
                            .addHeader("x-rapidapi-key", "b90a1daebemshb43c013454d216cp1a2d86jsn0c387bddeec4")
                            .addHeader("x-rapidapi-host", "call-of-duty-modern-warfare.p.rapidapi.com")
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String jsonData = response.body().string();
                        JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
                        //System.out.println(forecast.get("summary"));
                        JSONObject matches = (JSONObject) forecast.get("summary");
                        System.out.println(matches.get("all"));
                        JSONObject kill = (JSONObject) matches.get("all");
                        System.out.println(kill.get("scorePerMinute"));

                        for (int i = 0; i < 20; i++){
                            JSONObject site = (JSONObject)(((JSONArray)forecast.get("matches")).get(i));
                            JSONObject testo = (JSONObject)site.get("playerStats");
                            JSONObject testa = (JSONObject)site.get("weaponStats");
                            System.out.println(site.get("map"));
                            System.out.println(testa + " " + "fusille utilisé");
                            System.out.println(testo.get("kills") + " " + "kills");
                            System.out.println(testo.get("deaths")+ " " + "mort");
                            System.out.println(testo.get("accuracy")+ " " + "précision");
                            System.out.println(testo.get("scorePerMinute")+ " " + "Score par minute");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

*/