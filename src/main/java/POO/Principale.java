package POO;

public class Principale {
    public static void main(String[] args){
        Portefeuille portefeuilleRoot1 = new Portefeuille();
        Portefeuille portefeuilleClient1 = new Portefeuille();
        Portefeuille portefeuilleVendeur1 = new Portefeuille();

        Client premierClient = new Client("clats","deRue", portefeuilleClient1);
        Vendeur premierVendeur = new Vendeur("maxo","starling", portefeuilleVendeur1, premierClient);
        Root premierRoot = new Root("snitch", "tales", 0.,0.,0.,0., 0., premierVendeur, premierClient, portefeuilleRoot1);

        premierRoot.portefeuille.ajouterArgentSurCompte(1,84);
        premierRoot.portefeuille.ajouterArgentSurCompte(2,20);
        premierRoot.portefeuille.ajouterArgentSurDette(130);
        //premierRoot.portefeuille.ajouterArgentSurCreance(30);


        premierRoot.creditClient(30);

        premierVendeur.creditClient(20);
        premierVendeur.venteClient(20);
        premierVendeur.remboursementDeClient(10);


        System.out.println(premierClient);
        System.out.println(premierVendeur);
        System.out.println(premierRoot);
    }
}
