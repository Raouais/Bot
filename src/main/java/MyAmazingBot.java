import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramBot.ExecutionWebScrapping;

import java.io.FileNotFoundException;

public class MyAmazingBot extends TelegramLongPollingBot {
    private String userFirstName;
    private String userLastName;

    public String getUserFirstName(){
        return userFirstName;
    }

    public String getUserLastName(){
        return userLastName;
    }

    public void setUserFirstName(String userSurname){
        userFirstName = userSurname;
    }

    public void setUserLastName(String userName){
        userLastName = userName;
    }

    @Override
    public String getBotUsername() {
        return "like_420bot";
    }

    @Override
    public String getBotToken() {
        return "1136562319:AAFOEEBuFf95rbzwlQAmdA9E-x1SxdVF254";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = "@boTEST420";
            String recevier = update.getMessage().getText();
            setUserFirstName(update.getMessage().getFrom().getFirstName());
            setUserLastName(update.getMessage().getFrom().getLastName());

            ExecutionWebScrapping executionWebScrapping = new ExecutionWebScrapping();
            SendMessage sendMessage5 = new SendMessage();
            GestionClavier gestionClavier = new GestionClavier();

            System.out.println("L'utilisateur: " + getUserFirstName() + " " + getUserLastName() + " s'est connecté.");

            sendMessage5.setParseMode(ParseMode.MARKDOWN);
            sendMessage5.setChatId(chatId);



            if (recevier.equals("/hello")){
                System.out.println("hello de MyAmazingBot");
            } else {
                if (recevier.equals("/start") || recevier.equals("explications") || recevier.equals("Livres")){
                    try {
                        execute(gestionClavier.envoieMessage(update));
                    } catch (InterruptedException | TelegramApiException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (recevier.equals("parrainage")){
                        try {
                            execute(gestionClavier.envoiePhoto(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            sendMessage5.setText("Le bot vous propose quelques news :" + executionWebScrapping.execution(1));
                            execute(sendMessage5);
                        } catch (InterruptedException | TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}