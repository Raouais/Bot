package v2;

import java.io.IOException;
import java.sql.*;


//ajouter contractAddress
// et regarder pour ça
//https://api.polygonscan.com/api?module=account&action=tokentx&contractaddress=0xbbba073c31bf03b8acf7c28ef0738decf3695683&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&startblock=0&endblock=999999999&page=1&offset=1000&sort=asc&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP
public class Sql {
    public  static String url ="jdbc:mysql://localhost:3306/transactions?serverTimezone=UTC";
    public static String userName = "root";
    public static String password = "Ludov1c1.";

    String recupererAdresseWalletPersonne(long idTelegram){
        try {
            Connection con2 = DriverManager.getConnection(url,userName,password);
            String queryCheck = "select * from clients where idTelegram = ?";
            PreparedStatement ps = con2.prepareStatement(queryCheck);
            ps.setLong(1, idTelegram);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                //System.out.println("est présent : " + resultSet.getString("walletAdresse"));
                return resultSet.getString("walletAdresse");
            } else {
                return "pas présent";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "erreur";
    }

    boolean aWalletDejaPresent(long idTelegram){
        try {
            Connection con2 = DriverManager.getConnection(url,userName,password);
            String queryCheck = "select * from clients where idTelegram = ?";
            PreparedStatement ps = con2.prepareStatement(queryCheck);
            ps.setLong(1, idTelegram);
            ResultSet resultSet = ps.executeQuery();


            if (resultSet.next()){
                //System.out.println("est présent : " + resultSet.getString("walletAdresse"));
                return !resultSet.getString("walletAdresse").equalsIgnoreCase("none");
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void definirLeSolde(Date dateEntree){
        Date date = new Date();
        try {
            RequetteHttp requetteHttp = new RequetteHttp();
            double totalSolde = 0.;
            String resultatRequette0 = "null";
            String resultatRequette1 = "null";
            String resultatRequette2 = "null";
            String resultatRequette3 = "null";
            String resultatRequette4 = "null";
            String resultatRequette5 = "null";
            String resultatRequette6 = "null";
            // tant que supp à 600 000 et sarrete si = 2
            int nonce = 600000;
            for (int i = 0; nonce < 800000; i ++){
                resultatRequette0 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "nonce");
                int nonceConvertis = 0;
                nonceConvertis = Integer.parseInt(resultatRequette0);
                nonce = nonceConvertis;
                //System.out.println("nonce: " + nonce);
                if (nonce < 600000){
                    //System.out.println("pas prit en compte");
                } else {
                    resultatRequette1 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "value");
                    resultatRequette2 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "hash");
                    resultatRequette3 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "timeStamp");
                    resultatRequette4 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "from");
                    resultatRequette5 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "to");
                    resultatRequette6 = requetteHttp.retourneLeSoldessDeLaTransaction(i, "txreceipt_status");
                    try {
                        Connection con2 = DriverManager.getConnection(url,userName,password);
                        String queryCheck = "select * from matic where hash = ?";
                        PreparedStatement ps = con2.prepareStatement(queryCheck);
                        ps.setString(1, resultatRequette2);
                        ResultSet resultSet = ps.executeQuery();

                        if (resultSet.next()){
                            //System.out.println("hash déjà présent ");
                        } else {
                            // ajouter valueRewardStaking
                            Connection con = DriverManager.getConnection(url,userName,password);
                            String query = " insert into matic(value, hash, timeStamp, fromWallet, toWallet, txreceipt_status, soldeConvertis, dateDepart, dateRetrait, dateRetraitChoisie, montantStaker)"
                                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            PreparedStatement preparedStmt = con.prepareStatement(query);

                            double test1 = Double.parseDouble(resultatRequette1);
                            double exposantMoin18 = Math.pow(10.0,-18.0);
                            int test3 = Integer.parseInt(resultatRequette3);
                            int test6 = Integer.parseInt(resultatRequette6);
                            long timeStampAConvertir = Long.parseLong(resultatRequette3);


                            double montantMatic = test1 * exposantMoin18;
                            // problème optimisation
                            //System.out.println("le total!! : " + requetteHttp.calculMontantAcquisStaking(montantMatic));
                            preparedStmt.setDouble (1, test1 * exposantMoin18);
                            preparedStmt.setString (2, resultatRequette2);
                            preparedStmt.setInt   (3, test3);
                            preparedStmt.setString(4, resultatRequette4);
                            preparedStmt.setString(5, resultatRequette5);
                            preparedStmt.setInt(6, test6);
                            preparedStmt.setDouble(7, requetteHttp.calculMontantAcquis(montantMatic));
                            preparedStmt.setString(8, date.timeStampToGregorianCalendar(timeStampAConvertir));

                            // 1 récup jour de --> date.timeStampToGregorianCalendar(timeStampAConvertir)) 2 --> ajouter 14, 3 --> ajouter resultat dans BD
                            preparedStmt.setString(9, date.expirationBlocageStaking(14, timeStampAConvertir));
                            //preparedStmt.setString(9, date.expirationBlocageStaking());

                            // copie avant mais avec dateStakingChoisie
                            preparedStmt.setString(10, date.expirationBlocageStaking(dateEntree.getNbJoursEnStaking(), timeStampAConvertir));
                            //preparedStmt.setString(10, dateEntree.expirationBlocageStaking(dateEntree.getNbJoursEnStaking()));

                            // choisir la date choisirStaking au lieu de dateDeblockageStaking
                            preparedStmt.setDouble(11, requetteHttp.calculMontantAcquisStaking(montantMatic, dateEntree.getNbJoursEnStaking()));
                            System.out.println("voila la diff: " + requetteHttp.calculMontantAcquisStaking(montantMatic));
                            preparedStmt.execute();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // timeStamp
                    // from
                    // to
                    // txreceipt_status
                    totalSolde += Double.parseDouble(resultatRequette1);
                }
            }
            /*
            System.out.println("total = " + totalSolde * exposantMoin18);
            System.out.println("hash: " + resultatRequette2);
            System.out.println("timeStamp: " + resultatRequette3);
            System.out.println("from: " + resultatRequette4);
            System.out.println("to: " + resultatRequette5);
            System.out.println("txreceipt_status: " + resultatRequette6);
             */
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    double calculSoldePersonne(String fromWallet){
        double soldeTotal = 0;
        String resultatRequette0 = "null";
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from matic where fromWallet = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setString(1, fromWallet);
            ResultSet resultSet = ps.executeQuery();


            while (resultSet.next()){
                double soldeTransaction = 0;
                //System.out.println("premier element trouve " + fromWallet);
                //System.out.println("est présent ?: " + resultSet.getString("fromWallet"));
                resultatRequette0 = resultSet.getString("value");
                soldeTransaction = Double.parseDouble(resultatRequette0);
                //System.out.println(soldeTransaction);
                soldeTotal += soldeTransaction;
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        return soldeTotal;
    }

    void modifierPersonne(String adresseWallet, long idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set walletAdresse = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString   (1, adresseWallet);
            preparedStmt.setLong(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierPersonne(double solde, long idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set solde = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, solde);
            preparedStmt.setLong(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierPersonneSoldeDebite(double solde, double idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set soldeDebite = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, solde);
            preparedStmt.setDouble(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void ajoutPersonneBd(String prenom, String nom, long idTelegram, double solde, String walletAdresse, double soldeStaking, double soldeStakingTotal, double soldeStakingValide, double soldeStakingTotalValide){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from clients where idTelegram = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setLong(1, idTelegram);
            ResultSet resultSet = ps.executeQuery();
            /*
            while (resultSet.next()){
                System.out.println("est présent ?: " + resultSet.getLong("idTelegram"));
                //DELETE FROM clients WHERE prenom = "ludo"
            }
             */
            if (resultSet.next()){
                System.out.println("bonjour " + prenom);
            } else {
                System.out.println("bienvenue " + prenom);
                try {
                    //Connection con = DriverManager.getConnection(url,userName,password);
                    String query = " insert into clients(prenom, nom, idTelegram, solde, walletAdresse, soldeStaking, soldeStakingTotal, soldeStakingValide, soldeStakingTotalValide, soldeDebite)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString (1, prenom);
                    preparedStmt.setString (2, nom);
                    preparedStmt.setLong   (3, idTelegram);
                    preparedStmt.setDouble(4, solde);
                    preparedStmt.setString(5, walletAdresse);
                    preparedStmt.setDouble(6, soldeStaking);
                    preparedStmt.setDouble(7, soldeStakingTotal);
                    preparedStmt.setDouble(8, soldeStakingValide);
                    preparedStmt.setDouble(9, soldeStakingTotalValide);
                    preparedStmt.setDouble(10, 0.);
                    preparedStmt.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
    }

    void ajouterValeurBD(double aprReel, double aprInflation){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = " insert into valeur(aprReel, aprInflation, prixMatic, prixErowan, prixAtom, prixUsdt)"
                    + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble (1, aprReel);
            preparedStmt.setDouble (2, aprInflation);
            preparedStmt.setDouble (3, 0);
            preparedStmt.setDouble (4, 0);
            preparedStmt.setDouble (5, 0);
            preparedStmt.setDouble (6, 0);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    double recupererAprReel(){
        double tauxAprReel = 0;
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String queryCheck = "select * from valeur where aprReel > 0";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                tauxAprReel = resultSet.getDouble("aprReel");
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        return tauxAprReel;
    }

    void modifierValeurMaticBD(double prixMatic){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update valeur set prixMatic = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, prixMatic);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierValeurErowanBD(double prixErowan){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update valeur set prixErowan = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, prixErowan);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierValeurAtomBD(double prixAtom){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update valeur set prixAtom = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, prixAtom);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierValeurUsdtBD(double prixUsdt){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update valeur set prixUsdt = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, prixUsdt);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    double recupererPrixMatic(){
        double prixMatic = 0;
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String queryCheck = "select * from valeur where prixMatic > 0";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                prixMatic = resultSet.getDouble("prixMatic");
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        return prixMatic;
    }

    double recupererPrixErowan(){
        double prixErowan = 0;
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String queryCheck = "select * from valeur where prixErowan > 0";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                prixErowan = resultSet.getDouble("prixErowan");
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        return prixErowan;
    }

    double calculSoldeStakingPersonne(String fromWallet){
        double soldeTotalStaking = 0;
        String resultatRequette0 = "null";
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from matic where fromWallet = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setString(1, fromWallet);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                double soldeTransaction = 0;
                //System.out.println("premier element trouve " + fromWallet);
                //System.out.println("est présent ?: " + resultSet.getString("soldeConvertis"));
                //columnLabel "soldeStaking"
                resultatRequette0 = resultSet.getString("montantStaker");
                soldeTransaction = Double.parseDouble(resultatRequette0);
                //System.out.println(soldeTransaction);
                soldeTotalStaking += soldeTransaction;
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        //System.out.println("le montant vérouillé est de ------------------------------------------> :" + soldeTotalStaking);
        return soldeTotalStaking;
    }

    double calculSoldeStakingDeposePersonne(String fromWallet){
        double soldeTotalStaking = 0;
        String resultatRequette0 = "null";
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from matic where fromWallet = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setString(1, fromWallet);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                // ajouter if pour tester la date
                double soldeTransaction = 0;
                //System.out.println("premier element trouve " + fromWallet);
                //System.out.println("est présent ?: " + resultSet.getString("soldeConvertis"));
                //columnLabel "soldeStaking"
                resultatRequette0 = resultSet.getString("soldeConvertis");
                soldeTransaction = Double.parseDouble(resultatRequette0);
                //System.out.println(soldeTransaction);
                soldeTotalStaking += soldeTransaction;
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        //System.out.println("le montant total vérouillé est de ------------------------------------------> :" + soldeTotalStaking);
        return soldeTotalStaking;
    }

    //créer une aussi pour reward et pas Depose
    double calculSoldeStakingDeposePersonneValide(String fromWallet, Date dateEntree){
        double soldeTotalStaking = 0;
        String resultatRequette0 = "null";
        String resultatRequette1 = "null";
        int resultatRequette2 = 0;
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from matic where fromWallet = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setString(1, fromWallet);
            ResultSet resultSet = ps.executeQuery();

            Date date = new Date();
            String ajdh = date.moisEnCours();
            while (resultSet.next()){
                resultatRequette1 = resultSet.getString("dateRetraitChoisie");
                resultatRequette2 = resultSet.getInt("timeStamp");
                long timeStamp = resultatRequette2;

                //si la date d'ajdh est >= par rapport à dateRetraitChoisie
                if (date.calendrierMoisEnCours().after(date.calendrierExpirationBlocageStaking(dateEntree.getNbJoursEnStaking(), timeStamp)) || date.calendrierMoisEnCours().equals(date.calendrierExpirationBlocageStaking(dateEntree.getNbJoursEnStaking(), timeStamp))){
                    // ajouter if pour tester la date
                    double soldeTransaction = 0;
                    //System.out.println("premier element trouve " + fromWallet);
                    //System.out.println("est présent ?: " + resultSet.getString("soldeConvertis"));
                    //columnLabel "soldeStaking"
                    resultatRequette0 = resultSet.getString("soldeConvertis");

                    soldeTransaction = Double.parseDouble(resultatRequette0);
                    //System.out.println(soldeTransaction);
                    soldeTotalStaking += soldeTransaction;
                }
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        //System.out.println("le montant total vérouillé est de ------------------------------------------> :" + soldeTotalStaking);
        return soldeTotalStaking;
    }

    double calculSoldeStakingPersonneValide(String fromWallet, Date dateEntree){
        double soldeTotalStaking = 0;
        String resultatRequette0 = "null";
        String resultatRequette1 = "null";
        int resultatRequette2 = 0;
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from matic where fromWallet = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setString(1, fromWallet);
            ResultSet resultSet = ps.executeQuery();

            Date date = new Date();
            String ajdh = date.moisEnCours();
            while (resultSet.next()){
                resultatRequette1 = resultSet.getString("dateRetraitChoisie");
                resultatRequette2 = resultSet.getInt("timeStamp");
                long timeStamp = resultatRequette2;

                //si la date d'ajdh est >= par rapport à dateRetraitChoisie
                if (date.calendrierMoisEnCours().after(date.calendrierExpirationBlocageStaking(dateEntree.getNbJoursEnStaking(), timeStamp)) || date.calendrierMoisEnCours().equals(date.calendrierExpirationBlocageStaking(dateEntree.getNbJoursEnStaking(), timeStamp))){
                    // ajouter if pour tester la date
                    double soldeTransaction = 0;
                    //System.out.println("premier element trouve " + fromWallet);
                    //System.out.println("est présent ?: " + resultSet.getString("soldeConvertis"));
                    //columnLabel "soldeStaking"
                    resultatRequette0 = resultSet.getString("montantStaker");

                    soldeTransaction = Double.parseDouble(resultatRequette0);
                    //System.out.println(soldeTransaction);
                    soldeTotalStaking += soldeTransaction;
                }
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        //System.out.println("le montant total vérouillé est de ------------------------------------------> :" + soldeTotalStaking);
        return soldeTotalStaking;
    }

    void modifierSoldeStakingPersonneValide(double soldeStakingValide, long idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set soldeStakingValide = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, soldeStakingValide);
            preparedStmt.setLong(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void modifierSoldeStakingPersonne(double soldeStaking, long idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set soldeStaking = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, soldeStaking);
            preparedStmt.setLong(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierSoldeStakingPersonneTotal(double soldeStakingTotal, long idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set soldeStakingTotal = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, soldeStakingTotal);
            preparedStmt.setLong(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifierSoldeStakingPersonneTotalValide(double soldeStakingTotalValide, long idTelegram){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = "update clients set soldeStakingTotalValide = ? where idTelegram = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble   (1, soldeStakingTotalValide);
            preparedStmt.setLong(2, idTelegram);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    double recupererSoldeStakingValideEtTotalValide(double idTelegram){
        // prévoir ici l'ajout des données de retrait et renvoyer le total
        // problème car le idTelegram est pas géré.
        double soldeStakingValide = 0;
        double soldeStakingTotalValide = 0;
        double soldeDebite = 0;
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            // String queryCheck = "select * from matic where fromWallet = ?";
            //ps.setString(1, fromWallet);

            String queryCheck = "select * from clients where idTelegram = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setDouble(1, idTelegram);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                soldeDebite = resultSet.getDouble("soldeDebite");
                soldeStakingTotalValide = resultSet.getDouble("soldeStakingTotalValide");
                soldeStakingValide = resultSet.getDouble("soldeStakingValide");
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        System.out.println("soldeStakingValide = " + soldeStakingValide + " soldeStakingTotalValide = " + soldeStakingTotalValide);
        return ((soldeStakingValide + soldeStakingTotalValide) - soldeDebite);
    }

    void ajouterValeurRetraitBD(double idTelegram, double sommeRetire, String dateDuRetrait){
        try {
            Connection con = DriverManager.getConnection(url,userName,password);
            String query = " insert into retrait(idTelegram, sommeRetire, dateDuRetrait)"
                    + " values (?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setDouble (1, idTelegram);
            preparedStmt.setDouble (2, sommeRetire);
            preparedStmt.setString (3, dateDuRetrait);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    double calculSommeRetraitPersonne(double fromWallet){
        // changer les noms de variables
        double soldeTotalStaking = 0;
        String resultatRequette0 = "null";
        try {
            Connection con = DriverManager.getConnection(url,userName,password);

            String queryCheck = "select * from retrait where idTelegram = ?";
            PreparedStatement ps = con.prepareStatement(queryCheck);
            ps.setDouble(1, fromWallet);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                double soldeTransaction = 0;
                //columnLabel "soldeStaking"
                resultatRequette0 = resultSet.getString("sommeRetire");
                soldeTransaction = Double.parseDouble(resultatRequette0);
                //System.out.println(soldeTransaction);
                soldeTotalStaking += soldeTransaction;
            }
        } catch (SQLException i){
            i.printStackTrace();
        }
        return soldeTotalStaking;
    }
}
