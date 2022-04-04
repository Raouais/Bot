import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GestionClavier {
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

    public SendPhoto envoiePhoto(Update update) {
        long chat_id = update.getMessage().getChatId();
        SendPhoto sendPhoto = new SendPhoto();
        InputFile inputFile = new InputFile(new File("C:\\Users\\ludov\\Desktop\\photo_2022-01-16_17-52-22.jpg"));
        sendPhoto.setPhoto(inputFile);
        sendPhoto.setChatId(String.valueOf(chat_id));

        return sendPhoto;
    }

    public SendMessage envoieMessage(Update update) throws InterruptedException, FileNotFoundException {
        setUserFirstName(update.getMessage().getFrom().getFirstName());
        setUserLastName(update.getMessage().getFrom().getLastName());
        //testClavier
        Message messageBoard = update.getMessage();
        if (messageBoard.hasText()){
            String text = messageBoard.getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage sendMessage = new SendMessage();// Create a message object object
            sendMessage.setParseMode(ParseMode.MARKDOWN);
            sendMessage.setChatId(String.valueOf(chat_id));

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> KeyboardRowList = new ArrayList<>();
            KeyboardRow keyboardRow1 = new KeyboardRow();
            KeyboardRow keyboardRow2 = new KeyboardRow();
            KeyboardButton keyboardButton1 = new KeyboardButton();
            KeyboardButton keyboardButton2 = new KeyboardButton();
            KeyboardButton keyboardButton3 = new KeyboardButton();
            KeyboardButton keyboardButton4 = new KeyboardButton();
            keyboardButton1.setText("explications");
            keyboardButton2.setText("/start");
            keyboardButton3.setText("Livres");
            keyboardButton4.setText("parrainage");
            keyboardRow1.add(keyboardButton1);
            keyboardRow1.add(keyboardButton2);
            keyboardRow2.add(keyboardButton3);
            keyboardRow2.add(keyboardButton4);
            KeyboardRowList.add(keyboardRow1);
            KeyboardRowList.add(keyboardRow2);

            replyKeyboardMarkup.setKeyboard(KeyboardRowList);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

            if (text.equals("Livres")){
                sendMessage.setText("Adresse web de OneDrive: " + "https://1drv.ms/u/s!AoANnnRJuCS4hpF1kKl3oXEofKbeSQ?e=hXmMrG");
            }

            if (text.equals("explications")){
                sendMessage.setText("Ce bot vous donne des infos de finance en rapport avec le mot que vous tapez et contient un dossier partagé de livres.");
            }
            if (text.equals("/start")){
                if (getUserLastName() == null){
                    sendMessage.setText("Le bot de ludovic vous souhaite la bienvenue : " + getUserFirstName());
                } else {
                    sendMessage.setText("Le bot de ludovic vous souhaite la bienvenue : " + getUserFirstName() + " " + getUserLastName());
                }
            }
            if (text.equals("/test")){
                sendMessage.setChatId(String.valueOf(chat_id));
                sendMessage.setText("you send other thing");
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                inlineKeyboardButton1.setText("cliquez");
                inlineKeyboardButton1.setCallbackData("update_msg_text");
                rowInline.add(inlineKeyboardButton1);
                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                sendMessage.setReplyMarkup(markupInline);
            }
            return sendMessage;
        }
        return null;
    }
}

