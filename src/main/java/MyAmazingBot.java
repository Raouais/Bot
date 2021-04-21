import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MyAmazingBot extends TelegramLongPollingBot {

    private final SaveData saveData = new SaveData();

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
            var result = saveData.save(update.getMessage().getText());

            var message = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result))
                    .build();
            try {
                System.out.println("message executé");
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}