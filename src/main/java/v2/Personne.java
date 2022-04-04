package v2;


import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Personne {
    private String nom;
    private String prenom;
    private long idTelegram;
    private double solde;
    private String walletAdresse;
    private double soldeStaking;
    private double soldeStakingTotal;
    private double soldeStakingValide;
    private double soldeStakingTotalValide;
    FluxDeReponse fluxDeReponse;

    Personne(String nom, String prenom, double solde, String walletAdresse, double soldeStaking, double soldeStakingTotal, double soldeStakingValide, double soldeStakingTotalValide){
        this.nom = nom;
        this.prenom = prenom;
        this.solde = solde;
        this.walletAdresse = walletAdresse;
        this.soldeStaking = soldeStaking;
        this.soldeStakingTotal = soldeStakingTotal;
        this.soldeStakingValide = soldeStakingValide;
        this.soldeStakingTotalValide = soldeStakingTotalValide;
    }

    public void afficheClavier(){
        fluxDeReponse = new FluxDeReponse();
        //fluxDeReponse.walletFlow();


        try {
            TelegramBotsApi botsApi2 = new TelegramBotsApi(DefaultBotSession.class);
            botsApi2.registerBot(fluxDeReponse);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public double getSoldeStakingValide() {
        return soldeStakingValide;
    }

    public double getSoldeStakingTotalValide() {
        return soldeStakingTotalValide;
    }

    public void setSoldeStakingValide(double soldeStakingValide) {
        this.soldeStakingValide = soldeStakingValide;
    }

    public void setSoldeStakingTotalValide(double soldeStakingTotalValide) {
        this.soldeStakingTotalValide = soldeStakingTotalValide;
    }

    public double getSoldeStakingTotal() {
        return soldeStakingTotal;
    }

    public void setSoldeStakingTotal(double soldeStakingTotal) {
        this.soldeStakingTotal = soldeStakingTotal;
    }

    public double getSoldeStaking() {
        return soldeStaking;
    }

    public void setSoldeStaking(double soldeStaking) {
        this.soldeStaking = soldeStaking;
    }

    public void setNom(Update update){
        nom = update.getMessage().getFrom().getLastName();
    }

    public void setPrenom(Update update){
        prenom = update.getMessage().getFrom().getFirstName();
    }

    public void setIdTelegram(Update update) {
        this.idTelegram = update.getMessage().getFrom().getId();
    }

    public void setSolde(Double solde){
        this.solde = solde;
    }

    public void setWalletAdresse(String walletAdresse){
        this.walletAdresse = walletAdresse;
    }

    public String getNom() {
        if (nom == null){
            nom = "doe";
        }
        return nom;
    }

    public String getPrenom() {
        if (prenom == null){
            prenom = "john";
        }
        return prenom;
    }

    public long getIdTelegram() {
        return idTelegram;
    }

    public double getSolde(){
        return solde;
    }

    public String getWalletAdresse(){
        return walletAdresse;
    }
}
