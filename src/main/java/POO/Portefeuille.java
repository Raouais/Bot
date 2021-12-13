package POO;

public class Portefeuille {
    private Double compte1;
    private Double compte2;
    private Double dette;
    private Double creance;

    public Portefeuille(Double compte1, Double compte2, Double dette, Double creance){
        this.compte1 = compte1;
        this.compte2 = compte2;
        this.dette = dette;
        this.creance = creance;
    }

    public Portefeuille(){
        this(0., 0., 0., 0.);
    }

    public void setCreance(double nbAjoutCreance){
        creance = nbAjoutCreance;
    }

    public void setCompte2(double nbAjoutCompte2){
        compte2 = nbAjoutCompte2;
    }

    public void retirerArgentCreance(double nbAjoutRemboursement){
        creance -= nbAjoutRemboursement;
    }

    public void retirerArgentDette(double nbRetirerDette){
        dette = nbRetirerDette;
    }


    public void ajouterArgentSurCompte(int nbCompte, double nbAjoutArgent){
        if (nbCompte == 1){
            compte1 = nbAjoutArgent;
        } else {
            if (nbCompte == 2){
                compte2 = nbAjoutArgent;
            }
        }
    }

    public void retirerArgentSurCompte(int nbCompte, double nbAjoutArgent){
        if (nbCompte == 1){
            nbAjoutArgent = -nbAjoutArgent;
            compte1 = nbAjoutArgent;
        } else {
            if (nbCompte == 2){
                nbAjoutArgent = -nbAjoutArgent;
                compte2 = nbAjoutArgent;
            }
        }
    }

    public void ajouterArgentSurDette(double nbAjoutArgent){
        dette = nbAjoutArgent;
    }

    public void ajouterArgentSurCreance(double nbAjoutArgent){
        creance = nbAjoutArgent;
    }



    public Double total(){
        return compte1 + compte2 + creance - dette;
    }

    public String toString(){
        return "Portefeuille:-->" + " total= " + total() + "€" + " C1: " + compte1 + " C2: " + compte2 + " Creance: " + creance + " dette: " + dette;
    }
}
