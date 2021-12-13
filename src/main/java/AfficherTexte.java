public class AfficherTexte {
    String pourAchat(String message) {
        if (message.equals("achat")) {
            return "EntrerDansAfficherTexte ?";
        }
        return "exitAfficherTexte";
    }
}
