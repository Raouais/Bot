import org.java_websocket.client.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;

public class CustomThread  extends Thread {
    @Override
    public void run() {
        super.run();

        try {
            WebSocketClient client = new EmptyClient(new URI("wss://stream.binance.com:9443/ws/btcusdt@kline_1m"));
            client.connect();
            WebSocketClient client2 = new EmptyClient(new URI("wss://stream.binance.com:9443/ws/btcusdt@kline_30m"));
            client2.connect();
            WebSocketClient client3 = new EmptyClient(new URI("wss://stream.binance.com:9443/ws/btcusdt@kline_1w"));
            client3.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
