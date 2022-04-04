package telegramBot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class FluxDeReponse extends AbilityBot {
    public static String BOT_TOKEN = "1136562319:AAFOEEBuFf95rbzwlQAmdA9E-x1SxdVF254";
    public static String BOT_USERNAME = "like_420bot";

    int T_MAX = 10;
    String[] tests = new String[T_MAX];
    String[][] tests3x3 = new String[4][3];

    boolean estRegexValide = false;

    Personne ludo = new Personne("toto", "tata", 6.0, "none", 0, 0, 0, 0);

    double soldeTotalDeLaPersonne = 0;
    double soldeTotalDeLaPersonneEnStaking = 0;
    double soldeTotalDeLaPersonneEnStakingTOTAL = 0;
    double soldeTotalDeLaPersonneEnStakingValide = 0;
    double SoldeTotalDeLaPersonneEnStakingTOTALValide = 0;

    String bouton1 = "Discussion";
    String bouton2 = "Positions";
    String bouton3 = "Nouvelles économiques";
    String bouton4 = "Explications";
    String bouton5 = "Livres";
    String bouton6 = "Parrainage";
    String bouton7 = "Depot";
    String bouton8 = "Retrait";
    String bouton9 = "staking";
    String bouton10 = "/start";
    //String bouton11 = "Logs";
    //String bouton12 = "";

    int nbJours = 0;

    public void resetTable(){
        for (int i = 0; i < T_MAX; i++){
            tests[i] = null;
        }
    }


    public FluxDeReponse() {
        super(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public long creatorId() {
        return 1009740987;
    }

    public ReplyFlow walletFlow() {
        Reply saidLogs = Reply.of((AbilityBot, update) -> silent.send("Voici le résultats de la transaction", getChatId(update)),
                hasMessageWith("Transaction"));

        Reply saidDiscussion = Reply.of((AbilityBot, update) -> silent.send("https://t.me/forex_cyptoCommunication", getChatId(update)),
                hasMessageWith("channel1"));

        Reply saidPositions = Reply.of((AbilityBot, update) -> silent.send("https://t.me/groupetradingclemforex", getChatId(update)),
                hasMessageWith("channel2"));

        Reply saidNouvelles = Reply.of((AbilityBot, update) -> silent.send("https://t.me/Trading_news_2022", getChatId(update)),
                hasMessageWith("channel3"));

        Reply saidExplications = Reply.of((AbilityBot, update) -> silent.send("Avantages d'utiliser ce wallet: \n- wallet ledger donc + sécurisé. \n- wallet metamask donc même si les gens se trompent de réseau je peux récupérer les cryptos ?. \n- plus facile pour les gens d'investir dans le staking de liquidity car on d'abord créer le metamask avec une clé ledger ensuite virer sur le site sans se prendre de phising valdier le bon contract sans phising ensuite échanger 50% du solde sur quickswap en suite échanger nos 50/50 % cryptos en Lp token qui sont les token de liquidity et finalement ajouter ces Lp token au pool de liquidité.", getChatId(update)),
                hasMessageWith("wallet"));

        Reply saidLivres = Reply.of((AbilityBot, update) -> silent.send("Adresse web de OneDrive: " + "https://1drv.ms/u/s!AoANnnRJuCS4hpF1kKl3oXEofKbeSQ?e=hXmMrG", getChatId(update)),
                hasMessageWith("OneDrive"));

        Reply saidParrainage = Reply.of((AbilityBot, update) -> silent.send("Changer en photo", getChatId(update)),
                hasMessageWith("photos"));

        Reply saidDepot = Reply.of((AbilityBot, update) -> silent.send("0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4", getChatId(update)),
                hasMessageWith("revoirAdresse"));

        Reply saidSolde = Reply.of((AbilityBot, update) -> silent.send("Solde Matic: " + soldeTotalDeLaPersonne + "\nReward en staking: " + soldeTotalDeLaPersonneEnStaking + "\nFond bloqué en staking: " + soldeTotalDeLaPersonneEnStakingTOTAL, getChatId(update)),
                hasMessageWith("Solde"));

        Reply saidRetrait = Reply.of((AbilityBot, update) -> silent.send("A faire", getChatId(update)),
                hasMessageWith("Matic2"));


        ReplyFlow enregistrerValeurRetraitClient = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    Date date = new Date();
                    Sql sql = new Sql();
                    Personne temp = new Personne("a","b", 0,"null", 0, 0, 0, 0);
                    // mettre valeur update dans bd
                    // double ajouter idTelegram
                    temp.setIdTelegram(update);
                    double idTelegram = temp.getIdTelegram();
                    //double sommeRetire
                    String valeurRetire = update.getMessage().getText();
                    double sommeRetire = Double.parseDouble(valeurRetire);
                    // tester si sommeRetire est plus grand que solde
                    double solde = sql.recupererSoldeStakingValideEtTotalValide(idTelegram);
                    if (sommeRetire > solde){
                        resetTable();
                        Clavier clavier = new Clavier(tests, "erreur, vous avez essayez de trop retirer");
                        try {
                            sender.execute(clavier.affichageClavier(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // String dateDuRetrait
                        String dateDuRetrait = date.moisEnCours();
                        //sql.ajoutInfosBd
                        sql.ajouterValeurRetraitBD(idTelegram, sommeRetire, dateDuRetrait);

                        //faire total de la bd dans retrait et ensuite recup ça dans la fct recupererSoldeStakingValideEtTotalValide();
                        System.out.println("le montant total débité: " + sql.calculSommeRetraitPersonne(idTelegram));
                        sql.modifierPersonneSoldeDebite(sql.calculSommeRetraitPersonne(idTelegram), idTelegram);
                    }
                })
                .onlyIf(regularExpressionNombre())
                .build();

        ReplyFlow enregistrementStakingflow6 = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {

                    resetTable();
                    Clavier clavier = new Clavier(tests, "validé");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    nbJours = 365;


                })
                .onlyIf(hasMessageWith("365"))
                .build();

        ReplyFlow enregistrementStakingflow5 = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {

                    resetTable();
                    Clavier clavier = new Clavier(tests, "validé");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    nbJours = 180;


                })
                .onlyIf(hasMessageWith("180"))
                .build();

        ReplyFlow enregistrementStakingflow4 = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {

                    resetTable();
                    Clavier clavier = new Clavier(tests, "validé");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    nbJours = 90;


                })
                .onlyIf(hasMessageWith("90"))
                .build();

        ReplyFlow enregistrementStakingflow3 = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {

                    resetTable();
                    Clavier clavier = new Clavier(tests, "validé");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    nbJours = 60;


                })
                .onlyIf(hasMessageWith("60"))
                .build();

        ReplyFlow enregistrementStakingflow2 = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {

                    resetTable();
                    Clavier clavier = new Clavier(tests, "validé");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    nbJours = 30;


                })
                .onlyIf(hasMessageWith("30"))
                .build();


        ReplyFlow enregistrementStakingflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {

                    resetTable();
                    Clavier clavier = new Clavier(tests, "validé");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    nbJours = 14;


                })
                .onlyIf(hasMessageWith("14"))
                .build();

        ReplyFlow choixStakingflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    tests[0] = "14";
                    tests[1] = "30";
                    tests[2] = "60";
                    tests[3] = "90";
                    tests[4] = "180";
                    tests[5] = "365";
                    Clavier clavier = new Clavier(tests, "Choix des nombres de jours en staking");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton9))
                .next(enregistrementStakingflow)
                .next(enregistrementStakingflow2)
                .next(enregistrementStakingflow3)
                .next(enregistrementStakingflow4)
                .next(enregistrementStakingflow5)
                .next(enregistrementStakingflow6)
                .build();


        ReplyFlow testflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    Sql sql = new Sql();
                    Date date = new Date(nbJours);
                    resetTable();
                    tests[0] = "Solde";
                    tests[1] = "revoirAdresse";
                    Clavier clavier = new Clavier(tests, "0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    String ladresse = update.getMessage().getText();
                    ludo.setWalletAdresse(ladresse);

                    sql.modifierPersonne(ludo.getWalletAdresse(), ludo.getIdTelegram());

                    //System.out.println("encore la valeur verouillé: " + sql.calculSoldeStakingPersonne(ludo.getWalletAdresse()));

                    soldeTotalDeLaPersonneEnStakingValide = sql.calculSoldeStakingPersonneValide(ludo.getWalletAdresse(), date);
                    SoldeTotalDeLaPersonneEnStakingTOTALValide = sql.calculSoldeStakingDeposePersonneValide(ludo.getWalletAdresse(), date);

                    // ajouter les sql de DEPOT !!!!!!!
                    soldeTotalDeLaPersonneEnStaking = sql.calculSoldeStakingDeposePersonne(ludo.getWalletAdresse());
                    soldeTotalDeLaPersonneEnStakingTOTAL = sql.calculSoldeStakingDeposePersonne(ludo.getWalletAdresse());
                    soldeTotalDeLaPersonne = sql.calculSoldePersonne(ludo.getWalletAdresse());

                    sql.modifierSoldeStakingPersonneValide(soldeTotalDeLaPersonneEnStakingValide, ludo.getIdTelegram());
                    sql.modifierSoldeStakingPersonneTotalValide(SoldeTotalDeLaPersonneEnStakingTOTALValide, ludo.getIdTelegram());

                    sql.modifierSoldeStakingPersonne(soldeTotalDeLaPersonneEnStaking, ludo.getIdTelegram());
                    sql.modifierSoldeStakingPersonneTotal(soldeTotalDeLaPersonneEnStakingTOTAL, ludo.getIdTelegram());
                    sql.modifierPersonne(soldeTotalDeLaPersonne, ludo.getIdTelegram());
                })
                .onlyIf(regularExpression())
                .next(saidSolde)
                .next(saidDepot)
                .build();

        ReplyFlow logsflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    double soldeMatic = 0;
                    /*
                    try {
                        //System.out.println("rep expiration" + date.expirationBlocageStaking());
                        //System.out.println("voici le calcul de date" + date.calculDate());
                        //System.out.println("voici la date: " + date.anneeEnCours());
                        //System.out.println("voici le mois: " + date.moisEnCours());
                        RequetteHttp requetteHttp = new RequetteHttp();
                        //System.out.println("le total!! : " + requetteHttp.calculMontantAcquisStaking());
                        //System.out.println("prix rowan en eur: " + requetteHttp.recupererSoldeCosmos("erowan"));
                        //System.out.println("futur montant perçu: " + requetteHttp.calculMontantAcquis("ATOM", "erowan"));
                        //System.out.println("futur montant perçu: " + requetteHttp.calculMontantAcquis("MATIC", "erowan"));
                        //System.out.println("Voici le résultat de http recherche: " + requetteHttp.vaChercherResultSurPolygonscan(1));
                        //System.out.println("voici le résultat de http: " + requetteHttp.retourneLeStatusDeLaTransaction(1));
                        //System.out.println("solde: " + requetteHttp.recupererSoldeMatic());
                        soldeMatic = requetteHttp.recupererSoldeMatic();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                     */

                    resetTable();
                    tests[0] = "Transaction";
                    Clavier clavier = new Clavier(tests, "Menu logs, voici le solde du porteFeuille en ce moment : " + soldeMatic + " matic");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith("Logs"))
                .next(saidLogs).build();

        ReplyFlow discussionflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    Clavier clavier = new Clavier(tests, "https://t.me/forex_cyptoCommunication");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton1))
                .next(saidDiscussion).build();

        ReplyFlow positionsflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    Clavier clavier = new Clavier(tests, "https://t.me/groupetradingclemforex");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton2))
                .next(saidPositions).build();

        ReplyFlow nouvellesflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    Clavier clavier = new Clavier(tests, "https://t.me/Trading_news_2022");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton3))
                .next(saidNouvelles).build();

        ReplyFlow explicationsflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    tests[0] = "wallet";
                    Clavier clavier = new Clavier(tests, "explications");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton4))
                .next(saidExplications).build();

        ReplyFlow livresflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    tests[0] = "OneDrive";
                    Clavier clavier = new Clavier(tests, "ressources");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton5))
                .next(saidLivres).build();

        ReplyFlow parrainageflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    resetTable();
                    tests[0] = "photos";
                    Clavier clavier = new Clavier(tests, "parrainage");
                    try {
                        sender.execute(clavier.affichageClavier(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    long chat_id = update.getMessage().getChatId();
                    SendPhoto sendPhoto = new SendPhoto();
                    InputFile inputFile = new InputFile(new File("C:\\Users\\ludov\\Desktop\\original\\test1.png"));
                    sendPhoto.setPhoto(inputFile);
                    sendPhoto.setChatId(String.valueOf(chat_id));
                    try {
                        sender.sendPhoto(sendPhoto);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton6))
                .next(saidParrainage).build();

        ReplyFlow depotflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    Sql requeteSql = new Sql();
                    Date date = new Date(nbJours);

                    /*
                    System.out.println("system:" + date.getDateDuMoisEnCours());
                    System.out.println("system:" + date.getDateExpirationBlocageStaking());
                    System.out.println("system:" + date.getNbJoursEnStaking());
                    System.out.println("system:" + date.expirationBlocageStaking(date.getNbJoursEnStaking()));
                     */

                    requeteSql.definirLeSolde(date);

                    ludo.setNom(update);
                    ludo.setPrenom(update);
                    ludo.setIdTelegram(update);
                    ludo.setSolde(0.);
                    ludo.setWalletAdresse("none");
                    ludo.setSoldeStaking(0.);
                    ludo.setSoldeStakingTotal(0.);
                    ludo.setSoldeStakingValide(0.);
                    ludo.setSoldeStakingTotalValide(0.);


                    requeteSql.ajoutPersonneBd(ludo.getPrenom(), ludo.getNom(), ludo.getIdTelegram(), ludo.getSolde(), ludo.getWalletAdresse(), ludo.getSoldeStaking(), ludo.getSoldeStakingTotal(), ludo.getSoldeStakingValide(), ludo.getSoldeStakingTotalValide());

                    if (requeteSql.aWalletDejaPresent(ludo.getIdTelegram())){
                        ludo.setWalletAdresse(requeteSql.recupererAdresseWalletPersonne(ludo.getIdTelegram()));

                        System.out.println("Ce que j'attend ----->" + requeteSql.calculSoldeStakingDeposePersonneValide(ludo.getWalletAdresse(), date));
                        System.out.println("Ce que j'attend ----->" + requeteSql.calculSoldeStakingPersonneValide(ludo.getWalletAdresse(), date));

                        soldeTotalDeLaPersonneEnStakingValide = requeteSql.calculSoldeStakingPersonneValide(ludo.getWalletAdresse(), date);
                        SoldeTotalDeLaPersonneEnStakingTOTALValide = requeteSql.calculSoldeStakingDeposePersonneValide(ludo.getWalletAdresse(), date);

                        soldeTotalDeLaPersonneEnStaking = requeteSql.calculSoldeStakingPersonne(ludo.getWalletAdresse());
                        soldeTotalDeLaPersonneEnStakingTOTAL = requeteSql.calculSoldeStakingDeposePersonne(ludo.getWalletAdresse());
                        soldeTotalDeLaPersonne = requeteSql.calculSoldePersonne(ludo.getWalletAdresse());

                        requeteSql.modifierSoldeStakingPersonneValide(soldeTotalDeLaPersonneEnStakingValide, ludo.getIdTelegram());
                        requeteSql.modifierSoldeStakingPersonneTotalValide(SoldeTotalDeLaPersonneEnStakingTOTALValide, ludo.getIdTelegram());


                        requeteSql.modifierSoldeStakingPersonne(soldeTotalDeLaPersonneEnStaking, ludo.getIdTelegram());
                        requeteSql.modifierSoldeStakingPersonneTotal(soldeTotalDeLaPersonneEnStakingTOTAL, ludo.getIdTelegram());
                        requeteSql.modifierPersonne(soldeTotalDeLaPersonne, ludo.getIdTelegram());

                        resetTable();
                        tests[0] = "Solde";
                        Clavier clavier = new Clavier(tests, "adresse wallet: 0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4" + "\nLa votre: " + ludo.getWalletAdresse());
                        try {
                            sender.execute(clavier.affichageClavier(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        resetTable();
                        Clavier clavier = new Clavier(tests, "entrez votre adresse wallet");
                        try {
                            sender.execute(clavier.affichageClavier(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .onlyIf(hasMessageWith(bouton7))
                .next(testflow)
                .next(saidSolde)
                .build();

        ReplyFlow retraitflow = ReplyFlow.builder(db)
                .action((AbilityBot, update) -> {
                    Sql requeteSql = new Sql();
                    // 1 afficher le solde de la personne (sql)
                    System.out.println("solde = " + requeteSql.recupererSoldeStakingValideEtTotalValide(ludo.getIdTelegram()));
                    double solde = requeteSql.recupererSoldeStakingValideEtTotalValide(ludo.getIdTelegram());
                    // 2 vérifier que la personne possède un solde dispo positive pour faire un retrait ( if sql > 0 )

                    if (solde > 0){
                        resetTable();
                        Clavier clavier = new Clavier(tests, "combiens voulez vous retirer ?" + "\nvotre solde: " + solde);
                        try {
                            sender.execute(clavier.affichageClavier(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                    } else {
                        tests3x3[0][0] = "vide";
                        tests3x3[0][1] = "vide";
                        tests3x3[0][2] = "vide";
                        tests3x3[1][0] = "vide";
                        tests3x3[1][1] = "vide";
                        tests3x3[1][2] = "vide";
                        tests3x3[2][0] = "vide";
                        tests3x3[2][1] = "vide";
                        tests3x3[2][2] = "vide";
                        tests3x3[3][0] = "/start";

                        Clavier clavier = new Clavier(tests3x3);

                        try {
                            sender.execute(clavier.affichageClavierPreDefini(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    // 3 si bon ajouterRetrait dans la bd (insert into)
                    // 4 faire la somme en crypto des retrait ( += )
                    // 5 retirer du solde dispo cette somme
                })
                .onlyIf(hasMessageWith(bouton8))
                .next(enregistrerValeurRetraitClient)
                .next(saidRetrait).build();

        return ReplyFlow.builder(db)
                .action((AbilityBot, update)-> {
                    tests3x3[0][0] = bouton1;
                    tests3x3[0][1] = bouton2;
                    tests3x3[0][2] = bouton3;
                    tests3x3[1][0] = bouton4;
                    tests3x3[1][1] = bouton5;
                    tests3x3[1][2] = bouton6;
                    tests3x3[2][0] = bouton7;
                    tests3x3[2][1] = bouton8;
                    tests3x3[2][2] = bouton9;
                    tests3x3[3][0] = bouton10;

                    Clavier clavier = new Clavier(tests3x3);

                    try {
                        sender.execute(clavier.affichageClavierPreDefini(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                })
                .onlyIf(hasMessageWith(bouton10))
                .next(retraitflow)
                .next(depotflow)
                .next(parrainageflow)
                .next(livresflow)
                .next(explicationsflow)
                .next(nouvellesflow)
                .next(positionsflow)
                .next(discussionflow)
                .next(logsflow)
                .next(choixStakingflow)
                //.next(testflow)
                //.next(saidDiscussion)
                //.next(saidPositions)
                //.next(saidNouvelles)
                //.next(saidExplications)
                //.next(saidLivres)
                //.next(saidParrainage)
                //.next(saidDepot)
                //.next(saidRetrait)
                //.next(saidLogs)
                //.next(saidSolde)
                .build();
    }


    @NotNull
    private Predicate<Update> hasMessageWith(String msg) {
        return upd -> upd.getMessage().getText().equalsIgnoreCase(msg);
    }

    @NotNull
    private Predicate<Update> regularExpression() {
        String pattern = "^0x[a-fA-F0-9]{40}$";
        estRegexValide = true;
        return update -> update.getMessage().getText().matches(pattern);
    }

    @NotNull
    private Predicate<Update> regularExpressionNombre() {
        String pattern = "^[0-9]*.[0-9]*$";
        estRegexValide = true;
        return update -> update.getMessage().getText().matches(pattern);
    }
}

/*
    try {
        RequetteHttp requetteHttp = new RequetteHttp("https://api.polygonscan.com/api?module=account&action=balance&address=0x76Edc9d24Ff5d3da32044d6cacc8930Bf782a6B4&apikey=D41KQD69QQVW3HEB7TEE3SA4T3W7WGXNQP");
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
*/