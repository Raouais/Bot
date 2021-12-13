package POO;


public class Root {
    private String prenom;
    private String nom;

    Double nbQuantiteVendu;
    Double nbQuantiteAchat;
    Double nbQuantiteStock;
    Double nbQuantiteCredit;
    Double nbQuantiteRembourse;

    Vendeur vendeur;
    Client client;
    Portefeuille portefeuille;

    public Root(String prenom, String nom, Double nbQuantiteStock,
                Double nbQuantiteCredit, Double nbQuantiteAchat,
                Double nbQuantiteVendu, Double nbQuantiteRembourse,
                Vendeur vendeur, Client client, Portefeuille portefeuille){
        this.prenom = prenom;
        this.nom = nom;
        this.nbQuantiteStock = nbQuantiteStock;
        this.nbQuantiteCredit = nbQuantiteCredit;
        this.nbQuantiteAchat = nbQuantiteAchat;
        this.nbQuantiteVendu = nbQuantiteVendu;
        this.nbQuantiteRembourse = nbQuantiteRembourse;
        this.vendeur = vendeur;
        this.client = client;
        this.portefeuille = portefeuille;
    }

    public Root(String prenom, String nom, Vendeur vendeur, Client client, Portefeuille portefeuille){
        this(prenom, nom, 0., 0. ,0., 0., 0., vendeur, client, portefeuille);
    }

    public void achat(double nbQuantiteAchete){
        this.nbQuantiteAchat = nbQuantiteAchete;
        portefeuille.retirerArgentSurCompte(2,nbQuantiteAchete);
    }

    public void vente(double nbQuantiteVendu){
        this.nbQuantiteVendu = nbQuantiteVendu;
    }

    public void credit(Double nbQuantiteCredit){
        this.nbQuantiteCredit = nbQuantiteCredit;
        portefeuille.ajouterArgentSurDette(nbQuantiteCredit);
    }

    public void stock(double nbQuantiteStock){
        this.nbQuantiteStock = nbQuantiteStock;
    }

    public void rembourser(double nbQuantiteRembourse){
        this.nbQuantiteRembourse = nbQuantiteRembourse;
    }

    public void creditVendeur(double nbQuantiteCredite){
        vendeur.credit(nbQuantiteCredite);
        portefeuille.setCreance(nbQuantiteCredite);
        nbQuantiteVendu = nbQuantiteCredite;
    }

    public void venteVendeur(double nbQuantiteVente){
        vendeur.achat(nbQuantiteVente);
        portefeuille.setCompte2(nbQuantiteVente);
        nbQuantiteVendu = nbQuantiteVente;
    }

    public void remboursementDeVendeur(double nbQuantiteRembourse){
        vendeur.rembourser(nbQuantiteRembourse);
        portefeuille.retirerArgentCreance(nbQuantiteRembourse);
        portefeuille.setCompte2(nbQuantiteRembourse);
    }

    public void creditClient(double nbQuantiteCredite){
        client.credit(nbQuantiteCredite);
        portefeuille.setCreance(nbQuantiteCredite);
        nbQuantiteVendu = nbQuantiteCredite;
    }

    public double venteClient(double nbQuantiteVente){
        client.achat(nbQuantiteVente);
        portefeuille.setCompte2(nbQuantiteVente);
        nbQuantiteVendu = nbQuantiteVente;
        return nbQuantiteVendu;
    }

    public void remboursementDeClient(double nbQuantiteRembourse){
        client.rembourser(nbQuantiteRembourse);
        portefeuille.retirerArgentCreance(nbQuantiteRembourse);
        portefeuille.setCompte2(nbQuantiteRembourse);
    }

    public String toString(){
        return "Root: Qachat --> " + nbQuantiteAchat + " Qstock --> " + nbQuantiteStock + " Qcredit --> " + nbQuantiteCredit
                + " Qvente --> " + nbQuantiteVendu + " Qrembourse --> " + nbQuantiteRembourse + " P --> " +  portefeuille;
    }

    public String toString(String test){
        return "Root: Qachat --> " + nbQuantiteAchat + " Qstock --> " + nbQuantiteStock + " Qcredit --> " + nbQuantiteCredit
                + " Qvente --> " + nbQuantiteVendu + " Qrembourse --> " + nbQuantiteRembourse + " P --> " +  portefeuille + " " + test;
    }

}
