import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.net.URI;

public class EmptyClient extends WebSocketClient {

    public EmptyClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("new connection opened");
    }

    @Override
    public void onMessage(String s) {
        JSONObject forecast = (JSONObject) JSONValue.parse(s);
        String test = (String) forecast.get("i");
        System.out.println("received message depuis emptyClient: " + s);
        System.out.println("received message depuis emptyClient: " + test);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("connection terminee.");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("voici l'erreur" + e);
    }
}
