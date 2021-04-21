import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MyAmazingBot extends TelegramLongPollingBot {

    private final ScheduleService scheduleService = new ScheduleService();
    private final CompteAchat compteAchat = new CompteAchat();
    private final AfficherTexte afficherTexte = new AfficherTexte();
    private final SaveData saveData = new SaveData();

    static String premierUserFirstName;
//test
    public String user;

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
        System.out.println(update.getMessage().getText());
        System.out.println(update.getMessage().getFrom().getFirstName());
        System.out.println(update.getMessage().getFrom().getLastName());

        premierUserFirstName = update.getMessage().getFrom().getFirstName();
        var premierUserLastName = update.getMessage().getFrom().getLastName();
        user = premierUserFirstName;
        System.out.println(user + " voici le nom");

        String affichage = saveData.setCheminAccesFichier();
        System.out.println(affichage);

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (premierUserFirstName.equals("ludovic") && premierUserLastName.equals("schoonjans") || (premierUserLastName.equals("Alves"))) {
                System.out.println("ludovic schoonjans est connecté");
                    var result2 = compteAchat.achat(update.getMessage().getText());
                    var message2 = SendMessage.builder()
                            .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result2))
                            .build();
                    try {
                        System.out.println("ok");
                        execute(message2);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
            var result = scheduleService.proccess(update.getMessage().getText());
            var result4 = saveData.save(update.getMessage().getText());
            //var result2 = compteAchat.achat(update.getMessage().getText());
            var result3 = afficherTexte.pourAchat(update.getMessage().getText());

            var message = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result))
                    .build();

            /*var message2 = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result2))
                    .build();
                    a
             */

            var message3 = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result3))
                    .build();

            var message4 = SendMessage.builder()
                    .chatId(update.getMessage().getChatId().toString()).text(String.valueOf(result4))
                    .build();
            try {
                System.out.println(result4 + " result4");
                execute(message);
                //execute(message2);
                execute(message3);
                execute(message4);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}