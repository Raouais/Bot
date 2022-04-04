/*
    import org.json.simple.JSONObject;
    import org.json.simple.JSONValue;
    import org.telegram.abilitybots.api.bot.AbilityBot;
    import org.telegram.abilitybots.api.objects.Reply;
    import org.telegram.abilitybots.api.objects.ReplyFlow;
    import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
    import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
    import org.telegram.telegrambots.meta.api.objects.InputFile;

    import org.telegram.telegrambots.meta.api.objects.Update;
    import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
    import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
    import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

    import javax.validation.constraints.NotNull;

    import java.io.File;
    import java.io.IOException;
    import java.net.URI;
    import java.net.http.HttpClient;
    import java.net.http.HttpRequest;
    import java.net.http.HttpResponse;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.function.Predicate;

    import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

    public class ReplyFlowBot extends AbilityBot {
        public static String BOT_TOKEN = "1136562319:AAFOEEBuFf95rbzwlQAmdA9E-x1SxdVF254";
        public static String BOT_USERNAME = "like_420bot";

        public  static String url ="jdbc:mysql://localhost:3306/transactions?serverTimezone=UTC";
        public static String userName = "root";
        public static String password = "Ludov1c1.";

        // pour max date récupérer la dernière ligne de la table sql
        private int maxDate = 1640953507;

        private double soldeMatic = 0.;

        public int getMaxDate(){
            return maxDate;
        }

        public void setMaxDate(int dateMax){
            maxDate = dateMax;
        }

        public double getSoldeMatic(){
            return soldeMatic;
        }

        public void setSoldeMatic(double montant){
            soldeMatic = montant;
        }


        public ReplyFlowBot() {
            super(BOT_TOKEN, BOT_USERNAME);
        }

        @Override
        public long creatorId() {
            return 1009740987;
        }


        public ReplyFlow walletFlow() {
            /*
            Reply saidLogs = Reply.of((AbilityBot, update) -> silent.send("Voici le résultats de la transaction", getChatId(update)),
                    hasMessageWith("Transaction"));

            Reply saidDiscussion = Reply.of((AbilityBot, update) -> silent.send("https://t.me/forex_cyptoCommunication", getChatId(update)),
                    hasMessageWith("channel"));

            Reply saidPositions = Reply.of((AbilityBot, update) -> silent.send("https://t.me/groupetradingclemforex", getChatId(update)),
                    hasMessageWith("channel"));

            Reply saidNouvelles = Reply.of((AbilityBot, update) -> silent.send("https://t.me/Trading_news_2022", getChatId(update)),
                    hasMessageWith("channel"));

            Reply saidExplications = Reply.of((AbilityBot, update) -> silent.send("Ce bot vous donne des infos de finance en rapport avec le mot que vous tapez et contient un dossier partagé de livres.", getChatId(update)),
                    hasMessageWith("wallet"));

            Reply saidLivres = Reply.of((AbilityBot, update) -> silent.send("Adresse web de OneDrive: " + "https://1drv.ms/u/s!AoANnnRJuCS4hpF1kKl3oXEofKbeSQ?e=hXmMrG", getChatId(update)),
                    hasMessageWith("OneDrive"));

            Reply saidParrainage = Reply.of((AbilityBot, update) -> silent.send("Changer en photo", getChatId(update)),
                    hasMessageWith("photos"));

            Reply saidDepot = Reply.of((AbilityBot, update) -> silent.send("A faire", getChatId(update)),
                    hasMessageWith("Matic"));

            Reply saidRetrait = Reply.of((AbilityBot, update) -> silent.send("A faire", getChatId(update)),
                    hasMessageWith("Matic"));
             */


            /*
            ReplyFlow logsflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add("Transaction");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu logs");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        CustomThread customThread = new CustomThread();
                        customThread.start();
                    })
                    .onlyIf(hasMessageWith("Logs"))
                    .next(saidLogs).build();

            ReplyFlow discussionflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        //row.add("channel");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("https://t.me/forex_cyptoCommunication");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Discussion"))
                    .next(saidDiscussion).build();

            ReplyFlow positionsflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        //row.add("channel");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("https://t.me/groupetradingclemforex");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Positions"))
                    .next(saidPositions).build();

            ReplyFlow nouvellesflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        //row.add("channel");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("https://t.me/Trading_news_2022");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Nouvelles"))
                    .next(saidPositions).build();

            ReplyFlow explicationsflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add("wallet");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu des explications");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Explications"))
                    .next(saidExplications).build();

            ReplyFlow livresflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add("OneDrive");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu des livres");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Livres"))
                    .next(saidLivres).build();

            ReplyFlow parrainageflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add("photos");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu parrainage");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        long chat_id = update.getMessage().getChatId();
                        SendPhoto sendPhoto = new SendPhoto();
                        InputFile inputFile = new InputFile(new File("C:\\Users\\ludov\\Desktop\\photo_2022-01-16_17-52-22.jpg"));
                        sendPhoto.setPhoto(inputFile);
                        sendPhoto.setChatId(String.valueOf(chat_id));
                        try {
                            sender.execute(sendMessage);
                            sender.sendPhoto(sendPhoto);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Parrainage"))
                    .next(saidParrainage).build();

            ReplyFlow depotflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ApiPolygonscan apiPolygonscan = new ApiPolygonscan();
                        System.out.println("le résultat de la transaction est: " + apiPolygonscan.retourneLeStatusDeLaTransaction(5));
                        System.out.println("le resultat de tout: " + apiPolygonscan.vaChercherResultSurPolygonscan(5));
                        //test récupérer valeurMax pour boucle, tant que thx ok et testDeValeur > maxDate
                        int testDeValeur = Integer.parseInt(apiPolygonscan.vaChercherResultSurPolygonscan(4));
                        if (testDeValeur > getMaxDate()){
                            setMaxDate(testDeValeur);
                        }
                        System.out.println("la date max est: " + getMaxDate());

                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("https://api.polygonscan.com/api?module=account&action=balance&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP"))
                                .build();
                        try {
                            HttpResponse<String> response = client.send(request,
                                    HttpResponse.BodyHandlers.ofString());
                            System.out.println(response.body());
                            String jsonData = response.body();
                            JSONObject forecast = (JSONObject) JSONValue.parse(jsonData);
                            System.out.println(forecast.get("result"));
                            String test = (String) forecast.get("result");
                            double test2 = Double.parseDouble(test);
                            System.out.println(Math.pow(10.0,18.0));
                            double test3 = Math.pow(10.0,-18.0);
                            System.out.println(test3);
                            System.out.println("Le solde est de :" + test2 * test3);
                            double totalMatic = test2 * test3;
                            setSoldeMatic(totalMatic);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }

                        try {
                            Connection con = DriverManager.getConnection(url,userName,password);
                            Statement statement = con.createStatement();
                            String query = " insert into matic(nom, solde, walletDestinataire, walletExpediteur)"
                                    + " values (?, ?, ?, ?)";
                            PreparedStatement preparedStmt = con.prepareStatement(query);
                            preparedStmt.setString (1, "transaction");
                            preparedStmt.setDouble (2, getSoldeMatic());
                            preparedStmt.setString   (3, "adresse1");
                            preparedStmt.setString(4, "adresse2");
                            preparedStmt.execute();
                            //statement.execute("insert into matic(nom, solde, walletDestinataire, walletExpediteur) values('transaction2', testo, 'test1', 'test2')");
                            ResultSet result = statement.executeQuery("Select * from matic");
                            while (result.next()){
                                System.out.println("is: " + result.getString("id"));
                                System.out.println("le solde est de sur: " + result.getInt("solde"));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add("Matic");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu des dépôts");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Depot"))
                    .next(saidDepot).build();

            ReplyFlow retraitflow = ReplyFlow.builder(db)
                    .action((AbilityBot, update) -> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add("Matic");
                        row.add("/start");
                        keyboard.add(row);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu des retraits");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    })
                    .onlyIf(hasMessageWith("Retrait"))
                    .next(saidRetrait).build();
             */
            /*
            return ReplyFlow.builder(db)
                    .action((AbilityBot, update)-> {
                        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                        List<KeyboardRow> keyboard = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        KeyboardRow row2 = new KeyboardRow();
                        KeyboardRow row3 = new KeyboardRow();
                        KeyboardRow row4 = new KeyboardRow();
                        row.add("Discussion");
                        row.add("Positions");
                        row.add("Nouvelles");
                        row2.add("Explications");
                        row2.add("Livres");
                        row2.add("Parrainage");
                        row3.add("Depot");
                        row3.add("Retrait");
                        row3.add("Logs");
                        row4.add("/start");
                        keyboard.add(row);
                        keyboard.add(row2);
                        keyboard.add(row3);
                        keyboard.add(row4);
                        keyboardMarkup.setKeyboard(keyboard);
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                        sendMessage.setText("Menu d'accueil");
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        try {
                            sender.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        {
                            try {
                                Connection con = DriverManager.getConnection(url,userName,password);
                                Statement statement = con.createStatement();
                                ResultSet result = statement.executeQuery("Select * from matic where id = 1");
                                while (result.next()){
                                    System.out.println("is: " + result.getString("id"));
                                    System.out.println("le solde est de: " + result.getInt("solde"));
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                Connection con = DriverManager.getConnection(url,userName,password);
                                Statement statement = con.createStatement();
                                ResultSet result = statement.executeQuery("Select * from matic");
                                while (result.next()){
                                    System.out.println("from: " + result.getString("walletExpediteur"));
                                    System.out.println("to: " + result.getString("walletDestinataire"));
                                    System.out.println("le solde est de: " + result.getInt("solde"));
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .onlyIf(hasMessageWith("/start"))
                    .next(retraitflow)
                    .next(depotflow)
                    .next(parrainageflow)
                    .next(livresflow)
                    .next(explicationsflow)
                    .next(nouvellesflow)
                    .next(positionsflow)
                    .next(discussionflow)
                    .next(logsflow)
                    .next(saidDiscussion)
                    .next(saidPositions)
                    .next(saidNouvelles)
                    .next(saidExplications)
                    .next(saidLivres)
                    .next(saidParrainage)
                    .next(saidDepot)
                    .next(saidRetrait)
                    .next(saidLogs)
                    .build();
        }

        @NotNull
        private Predicate<Update> hasMessageWith(String msg) {
            return upd -> upd.getMessage().getText().equalsIgnoreCase(msg);
        }
    }
*/