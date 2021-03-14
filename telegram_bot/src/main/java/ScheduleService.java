import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScheduleService {
    private  final Pattern pattern = Pattern.compile("test", Pattern.CASE_INSENSITIVE + Pattern.UNIX_LINES);

    List<String> proccess(String message) {
        var matcher = pattern.matcher(message);
        int i = 0;
        var list = new ArrayList<String>();
        while (matcher.find()) {
            for (int j = 0; j <= matcher.groupCount(); j++) {
                list.add("group " + i + ": " + matcher.group(j));
                i++;
            }
        }
        return list;
    }
}
