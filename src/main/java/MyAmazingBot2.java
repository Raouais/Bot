import POO.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import org.json.JSONObject;

//Module création d'objet

public class MyAmazingBot2 extends TelegramLongPollingBot {
    Portefeuille portefeuilleRoot1 = new Portefeuille();
    Portefeuille portefeuilleClient1 = new Portefeuille();
    Portefeuille portefeuilleVendeur1 = new Portefeuille();




    Client premierClient = new Client("clats","deRue", portefeuilleClient1);
    Vendeur premierVendeur = new Vendeur("maxo","starling", portefeuilleVendeur1, premierClient);
    Root premierRoot = new Root("snitch", "tales", 0.,0.,0.,0., 0., premierVendeur, premierClient, portefeuilleRoot1);

    AjouterDonnees premierAjout = new AjouterDonnees();


    private final ScheduleService scheduleService = new ScheduleService();
    private final CompteAchat compteAchat = new CompteAchat();
    private final AfficherTexte afficherTexte = new AfficherTexte();
    private final SaveData saveData = new SaveData();

    private boolean estAchat = false;
    private boolean estVendue = false;
    private boolean estCredit = false;


    // variable du nom test git !!
    //testtt
    static String premierUserFirstName;
    public String user;
    public String testReceiver;


    @Override
    public String getBotUsername() {
        return "like_420bot";
    }

    @Override
    public String getBotToken() {
        return "1136562319:AAFOEEBuFf95rbzwlQAmdA9E-x1SxdVF254";
    }

    public void setEstAchat(boolean estAchatParam){
        estAchat = estAchatParam;
    }
    public void setEstVendue(boolean estVendueParam){
        estVendue = estVendueParam;
    }
    public void setEstCredit(boolean estCreditParam){
        estCredit = estCreditParam;
    }


    public void setDefaultAchatVendueCredit(){
        if (testReceiver.equals("achat")){
            System.out.println("On est en achat");
            setEstAchat(true);
            setEstVendue(false);
            setEstCredit(false);
            double varDouble = 0;
            String a = "" + varDouble;
            testReceiver = a;
        }
        if (testReceiver.equals("vente")){
            System.out.println("On est en vente");
            setEstAchat(false);
            setEstVendue(true);
            setEstCredit(false);
            double varDouble = 0;
            String b = "" + varDouble;
            testReceiver = b;

        }
        if (testReceiver.equals("credit")){
            System.out.println("On est en credit");
            setEstAchat(false);
            setEstVendue(false);
            setEstCredit(true);
            double varDouble = 0;
            String c = "" + varDouble;
            testReceiver = c;
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        /*
        System.out.println(update.getMessage().getText());
        System.out.println(update.getMessage().getFrom().getFirstName());
        System.out.println(update.getMessage().getFrom().getLastName());
        */
        //problème de getMessage car ils sont vide car getQuerry plus de message.

        if (update.hasMessage() && update.getMessage().hasText()) {
            String recevier = update.getMessage().getText();

            testReceiver = recevier;


            premierUserFirstName = update.getMessage().getFrom().getFirstName();
            var premierUserLastName = update.getMessage().getFrom().getLastName();
            user = premierUserFirstName;
            System.out.println(user + " voici le nom");

            String affichage = saveData.getCheminAccesFichier();
            System.out.println(affichage);

            var result = scheduleService.proccess(update.getMessage().getText());
            var message = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result))
                    .build();

            var result3 = afficherTexte.pourAchat(update.getMessage().getText());
            var message3 = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result3))
                    .build();
            if (premierUserFirstName.equals("ludovic") && premierUserLastName.equals("schoonjans") || (premierUserLastName.equals("Alves"))) {
                System.out.println("Root est connecté");
                var result2 = compteAchat.achat(update.getMessage().getText());
                var message2 = SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result2))
                        .build();

                var result4 = saveData.save(update.getMessage().getText());

                var result5  = scheduleService.proccess2(update.getMessage().getText());




                var message4 = SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result4))
                        .build();
                var message5 = SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result5))
                        .build();

                //test avec POO

                //perso

                //portefeuilleRoot1.ajouterArgentSurCompte(1,84);
                //portefeuilleRoot1.ajouterArgentSurCompte(2,20);
                //portefeuilleRoot1.ajouterArgentSurDette(130);
                //portefeuilleRoot1.ajouterArgentSurCreance(30);

                //portefeuilleRoot1.retirerArgentSurCompte(1,30);
                //portefeuilleRoot1.retirerArgentSurCompte(1,80);

                //


                try {
                    premierAjout.setConnection();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    premierAjout.setStatement(202, premierRoot.venteClient(result));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                // root vend à vendeur
                premierRoot.venteVendeur(result);
                // root vend à client
                premierRoot.venteClient(result);


                System.out.println(premierClient);
                System.out.println(premierVendeur);
                System.out.println(premierRoot);

                var result10 = premierRoot.toString(update.getMessage().getText());
                var result11 = premierVendeur.toString(update.getMessage().getText());
                var result12 = premierClient.toString(update.getMessage().getText());


                var test10 = SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result10))
                        .build();
                var test11 = SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result11))
                        .build();
                var test12 = SendMessage.builder()
                        .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result12))
                        .build();

                    try {
                        execute(message2);
                        execute(message);
                        execute(message3);
                        execute(message4);
                        execute(message5);
                        execute(test10);
                        execute(test11);
                        execute(test12);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            } else {
                if (premierUserFirstName.equals("Jean") && premierUserLastName.equals("Mathieu")){
                    System.out.println("Vendeur est connecté");
                    //System.out.println(saveData.save(recevier));
                    //setDefaultAchatVendueCredit();
                    CallOfDuty callOfDuty1 = new CallOfDuty();
                    System.out.println("call of duty multiplayer " + callOfDuty1);
                    //testClavier

                    Message messageBoard = update.getMessage();
                    if (messageBoard.hasText()){
                        String text = messageBoard.getText();
                        long chat_id = update.getMessage().getChatId();
                        if (text.equals("/start")){

                            SendMessage sendMessage = new SendMessage();
                            sendMessage.setText("test");
                            sendMessage.setParseMode(ParseMode.MARKDOWN);
                            sendMessage.setChatId(String.valueOf(chat_id));

                            //test1
                            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                            List<KeyboardRow> KeyboardRowList = new ArrayList<>();
                            KeyboardRow keyboardRow1 = new KeyboardRow();
                            KeyboardButton keyboardButton1 = new KeyboardButton();
                            keyboardButton1.setText("testA" + callOfDuty1);
                            keyboardRow1.add(keyboardButton1);
                            KeyboardRowList.add(keyboardRow1);

                            replyKeyboardMarkup.setKeyboard(KeyboardRowList);
                            sendMessage.setReplyMarkup(replyKeyboardMarkup);




                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        } else {
                            SendMessage sendMessage = new SendMessage(); // Create a message object object
                            sendMessage.setChatId(String.valueOf(chat_id));
                            sendMessage.setText("you send other thing");
                            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                            List<InlineKeyboardButton> rowInline = new ArrayList<>();
                            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                            inlineKeyboardButton1.setText("Update message text");
                            inlineKeyboardButton1.setCallbackData("update_msg_text");
                            rowInline.add(inlineKeyboardButton1);
                            // Set the keyboard to the markup
                            rowsInline.add(rowInline);
                            // Add it to the message
                            markupInline.setKeyboard(rowsInline);
                            sendMessage.setReplyMarkup(markupInline);
                            try {
                                execute(sendMessage); // Sending our message object to user
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            
                        }
                    }
                    //saveData.save(recevier);

                    //Vendeur achette à root
                    if (estAchat){
                        System.out.println("achat");
                        System.out.println("le vendeur: " + premierVendeur + " a acheté avant");
                        premierRoot.venteVendeur(saveData.save(recevier));
                        //premierRoot.venteVendeur(result);
                    }

                    //Vendeur vend à client
                    if (estVendue){
                        System.out.println("vente");
                        System.out.println("le vendeur: " + premierVendeur + " a vendu avant");
                        //premierVendeur.venteClient(saveData.save(recevier));
                        premierVendeur.venteClient(saveData.save(recevier));
                    }





                    var result100 = premierVendeur.toString(update.getMessage().getText());
                    var message100 = SendMessage.builder()
                            .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result100))
                            .build();
                    System.out.println("le vendeur: " + premierVendeur + " a vendu et acheté après");
                    try {
                        execute(message100);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {

                    System.out.println("Client est connecté");
                    //Client achette à vendeur
                    premierVendeur.venteClient(result);

                    var result120 = premierClient.toString(update.getMessage().getText());
                    var message120 = SendMessage.builder()
                            .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result120))
                            .build();
                    System.out.println("le client: " + premierClient + " a acheté");
                    try {
                        execute(message120);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()){
            String call_data = update.getCallbackQuery().getData();
            int message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            System.out.println("la chaine de caract callBack: " + call_data);

            if (call_data.equals("update_msg_text")) {

                String answer = "Updated message text";
                System.out.println(answer);


                EditMessageText new_message = new EditMessageText();
                new_message.setChatId(String.valueOf(chat_id));
                new_message.setMessageId(message_id);
                new_message.setText(answer);
                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //Security.insertProviderAt(Conscrypt.newProvider(), 1);
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
            }
        }
    }
}