import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Flag.MESSAGE;
import static org.telegram.abilitybots.api.objects.Flag.REPLY;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class MultipleCommande extends AbilityBot {
    public static String BOT_TOKEN = "1136562319:AAFOEEBuFf95rbzwlQAmdA9E-x1SxdVF254";
    public static String BOT_USERNAME = "like_420bot";

    public MultipleCommande() {
        super(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public long creatorId() {
        return 1009740987;
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    List<KeyboardRow> keyboard = new ArrayList<>();
                    KeyboardRow row = new KeyboardRow();
                    row.add("Row 1 Button 1");
                    row.add("Row 1 Button 2");
                    row.add("Row 1 Button 3");
                    keyboard.add(row);
                    keyboardMarkup.setKeyboard(keyboard);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(ctx.chatId()));
                    sendMessage.setText("Hello World !");
                    sendMessage.setReplyMarkup(keyboardMarkup);

                    try {
                        Message test = sender.execute(sendMessage);
                        String testA = String.valueOf(test);
                        System.out.println("test AAAAAAA " + testA);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .build();
    }
    public Ability playWithMe() {
        String playMessage = "Play with me!";

        return Ability.builder()
                .name("play")
                .info("Do you want to play with me?")
                .privacy(PUBLIC)
                .locality(ALL)
                .input(0)
                .action(ctx -> silent.forceReply(playMessage, ctx.chatId()))

                // The signature of a reply is -> (Consumer<Update> action, Predicate<Update>... conditions)
                // So, we  first declare the action that takes an update (NOT A MESSAGECONTEXT) like the action above
                // The reason of that is that a reply can be so versatile depending on the message, context becomes an inefficient wrapping
                .reply((baseAbilityBot, update) -> {
                            // Prints to console
                            System.out.println("I'm in a reply!");
                            // Sends message
                            silent.send("It's been nice playing with you!", update.getMessage().getChatId());
                        },
                        // Now we start declaring conditions, MESSAGE is a member of the enum Flag class
                        // That class contains out-of-the-box predicates for your replies!
                        // MESSAGE means that the update must have a message
                        // This is imported statically, Flag.MESSAGE
                        MESSAGE,
                        // REPLY means that the update must be a reply, Flag.REPLY
                        REPLY,
                        // A new predicate user-defined
                        // The reply must be to the bot
                        isReplyToBot(),
                        // If we process similar logic in other abilities, then we have to make this reply specific to this message
                        // The reply is to the playMessage
                        isReplyToMessage(playMessage)
                )
                .build();
    }

    private Predicate<Update> isReplyToMessage(String message) {
        return upd -> {
            Message reply = upd.getMessage().getReplyToMessage();
            return reply.hasText() && reply.getText().equalsIgnoreCase(message);
        };
    }

    private Predicate<Update> isReplyToBot() {
        return upd -> upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    }
}
