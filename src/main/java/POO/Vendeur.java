package POO;

public class Vendeur {
    private String prenom;
    private String nom;

    private Double nbQuantiteVendu;
    private Double nbQuantiteAchat;
    private Double nbQuantiteStock;
    private Double nbQuantiteCredit;
    private Double nbQuantiteRembourse;

    private Portefeuille portefeuille;
    private Client client;

    public Vendeur(String prenom, String nom, Double nbQuantiteStock, Double nbQuantiteCredit, Double nbQuantiteAchat, Double nbQuantiteVendu,
                   Double nbQuantiteRembourse, Portefeuille portefeuille, Client client){
        this.prenom = prenom;
        this.nom = nom;
        this.nbQuantiteStock = nbQuantiteStock;
        this.nbQuantiteCredit = nbQuantiteCredit;
        this.nbQuantiteAchat = nbQuantiteAchat;
        this.nbQuantiteVendu = nbQuantiteVendu;
        this.nbQuantiteRembourse = nbQuantiteRembourse;
        this.portefeuille = portefeuille;
        this.client = client;
    }

    public Double getNbQuantiteCredit(){
        return nbQuantiteCredit;
    }

    public Vendeur(String prenom, String nom, Portefeuille portefeuille, Client client){
        this(prenom, nom, 0., 0. ,0., 0., 0., portefeuille, client);
    }

    public void achat(double nbQuantiteAchete){
        this.nbQuantiteAchat = nbQuantiteAchete;
        portefeuille.retirerArgentSurCompte(1, nbQuantiteAchete);
    }

    public void vente(double nbQuantiteVendu){
        this.nbQuantiteVendu = nbQuantiteVendu;
        portefeuille.ajouterArgentSurCompte(2,nbQuantiteVendu);
    }

    public void credit(double nbQuantiteCredit){
        this.nbQuantiteCredit = nbQuantiteCredit;
        portefeuille.ajouterArgentSurDette(nbQuantiteCredit);
    }

    public void stock(double nbQuantiteStock){
        this.nbQuantiteStock += nbQuantiteStock;
    }

    public void rembourser(double nbQuantiteRembourse){
        this.nbQuantiteRembourse = nbQuantiteRembourse;
        portefeuille.retirerArgentDette(nbQuantiteRembourse);
    }

    public void remboursementDeClient(double nbQuantiteRembourse){
        client.rembourser(nbQuantiteRembourse);
        portefeuille.retirerArgentCreance(nbQuantiteRembourse);
        portefeuille.setCompte2(nbQuantiteRembourse);
    }

    public void creditClient(double nbQuantiteCredite){
        client.credit(nbQuantiteCredite);
        portefeuille.setCreance(nbQuantiteCredite);
        nbQuantiteVendu = nbQuantiteCredite;
    }

    public void venteClient(double nbQuantiteVente){
        client.achat(nbQuantiteVente);
        portefeuille.setCompte2(nbQuantiteVente);
        nbQuantiteVendu = nbQuantiteVente;
    }




    public String toString(){
        return "Vendeur: Qachat --> " + nbQuantiteAchat + " Qstock --> " + nbQuantiteStock + " Qcredit --> " + nbQuantiteCredit
                + " Qvente --> " + nbQuantiteVendu + " Qrembourse --> " + nbQuantiteRembourse + " P --> " +  portefeuille;
    }

    public String toString(String test){
        return "Vendeur: Qachat --> " + nbQuantiteAchat + " Qstock --> " + nbQuantiteStock + " Qcredit --> " + nbQuantiteCredit
                + " Qvente --> " + nbQuantiteVendu + " Qrembourse --> " + nbQuantiteRembourse + " P --> " +  portefeuille + " " + test;
    }
}
