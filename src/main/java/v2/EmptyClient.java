package v2;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URI;

public class EmptyClient extends WebSocketClient {
    String infos = "null";

    public EmptyClient(URI serverUri) {
        super(serverUri);
    }

    public String getInfos() {
        return infos;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("new connection opened");
    }

    @Override
    public void onMessage(String s) {
        JSONObject forecast = (JSONObject) JSONValue.parse(s);
        String test = (String) forecast.get("s");
        System.out.println("received message depuis emptyClient: " + s);
        System.out.println("received message depuis emptyClient: " + test);
        JSONObject resultat = (JSONObject) forecast.get("k");
        JSONObject forecast2 = (JSONObject) JSONValue.parse(String.valueOf(resultat));
        String test2 = (String) forecast2.get("o");
        String test3 = (String) forecast2.get("c");
        System.out.println(test2 + " " + test3);
        infos = test3;
        //send(String.valueOf(forecast));
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("connection terminee.");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("voici l'erreur" + e);
    }

    @Override
    public String toString(){
        return "voici les infos ---> " + getInfos();
    }
}
