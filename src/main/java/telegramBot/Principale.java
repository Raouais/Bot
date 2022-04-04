package telegramBot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Principale {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi2 = new TelegramBotsApi(DefaultBotSession.class);
            FluxDeReponse fluxDeReponse = new FluxDeReponse();
            botsApi2.registerBot(fluxDeReponse);
            ThreadPrix threadPrix = new ThreadPrix();
            threadPrix.start();
            // il manque le reward réel pour ça il faut créer une table historique des apr, y stocker chaque jours l'info et quand on demande l'apr réel on additionne l'apr de chaque jour et on divise par nbJours
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
