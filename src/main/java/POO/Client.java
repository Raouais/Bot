package POO;

public class Client {
    private String prenom;
    private String nom;

    private Double nbQuantiteAchat;
    private Double nbQuantiteCredit;
    private Double nbQuantiteRembourse;

    Client client;
    private Portefeuille portefeuille;

    public Client(String prenom, String nom, Double nbQuantiteCredit, Double nbQuantiteAchat,  Double nbQuantiteRembourse, Portefeuille portefeuille){
        this.prenom = prenom;
        this.nom = nom;
        this.nbQuantiteCredit = nbQuantiteCredit;
        this.nbQuantiteAchat = nbQuantiteAchat;
        this.nbQuantiteRembourse = nbQuantiteRembourse;
        this.portefeuille = portefeuille;
    }

    public Client(String prenom, String nom, Portefeuille portefeuille){
        this(prenom, nom, 0., 0. ,0., portefeuille);
    }

    public void achat(double nbQuantiteAchete){
        this.nbQuantiteAchat += nbQuantiteAchete;
        portefeuille.retirerArgentSurCompte(2,nbQuantiteAchete);
    }


    public void credit(double nbQuantiteCredit){
        this.nbQuantiteCredit += nbQuantiteCredit;
        portefeuille.ajouterArgentSurDette(nbQuantiteCredit);
    }


    public void rembourser(double nbQuantiteRembourse){
        this.nbQuantiteRembourse += nbQuantiteRembourse;
        portefeuille.retirerArgentDette(nbQuantiteRembourse);
    }


    public String toString(){
        return "Client: Qachat --> " + nbQuantiteAchat  + " Qcredit --> " + nbQuantiteCredit
                 + " Qrembourse --> " + nbQuantiteRembourse + " P --> " +  portefeuille;
    }

    public String toString(String test){
        return "Client: Qachat --> " + nbQuantiteAchat  + " Qcredit --> " + nbQuantiteCredit
                + " Qrembourse --> " + nbQuantiteRembourse + " P --> " +  portefeuille;
    }


}
