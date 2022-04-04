package telegramBot;

import okhttp3.*;
import java.io.IOException;

public class Notion {

    public void queryDB() throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"page_size\":1}", mediaType);
        Request request = new Request.Builder()
                .url("https://api.notion.com/v1/databases/f2838fe47fc841f38de61916f6e573c6/query")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Notion-Version", "2022-02-22")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer secret_YGxPFZWTD3EUXSTLNJwFqRNnwPp5F2OEM79pzJcGMpW")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
    }
}
