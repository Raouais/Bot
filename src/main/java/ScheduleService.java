
import java.util.regex.Pattern;

public class ScheduleService {
    private  final Pattern pattern = Pattern.compile("[0-9]+[\\\\.]?[0-9]*", Pattern.CASE_INSENSITIVE + Pattern.UNIX_LINES);
    private final Pattern pattern2 = Pattern.compile("[a-z]",Pattern.CASE_INSENSITIVE + Pattern.UNIX_LINES);


    double proccess(String message) {
        var matcher = pattern.matcher(message);
        if (matcher.find()) {
            System.out.println("on a trouvé des nombres " + matcher.find());
            return Double.parseDouble(message);
        }
    return 0;
    }

    String proccess2(String message) {
        var matcher = pattern2.matcher(message);
        if (matcher.find()) {
            System.out.println("on a trouvé des caractères " + matcher.find());
            return message + "passeParBot";
        }
    return "none";
    }
}
