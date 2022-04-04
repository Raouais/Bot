package v2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Prix {

    private static String apiKey = "479e5ce8-e6f7-4bf0-842b-7e16c32ef91e";

    public String execution(String symbol){
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?CMC_PRO_API_KEY=479e5ce8-e6f7-4bf0-842b-7e16c32ef91e&symbol=" + symbol + "&convert=eur";
        List<NameValuePair> paratmers = new ArrayList<>();
        /*
        paratmers.add(new BasicNameValuePair("start","1"));
        paratmers.add(new BasicNameValuePair("limit","5000"));
        paratmers.add(new BasicNameValuePair("convert","USD"));
         */

        try {
            return makeAPICall(uri, paratmers);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "fail";
    }


    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        try (CloseableHttpResponse response = client.execute(request)) {
            //System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }
        return response_content;
    }

}