package telegramBot;

import java.io.IOException;

public class ThreadPrix  extends Thread {
    ExecutionWebScrapping executionWebScrapping = new ExecutionWebScrapping();
    Sql sql = new Sql();
    String apr1;
    String apr2;

    @Override
    public void run() {
        super.run();


        // pouvoir relancer tous les jours
        try {
            for (int i = 0; i < 288; i++){
                try {
                    RequetteHttp requetteHttp = new RequetteHttp();
                    double rq1 = requetteHttp.recupererSoldeCosmos("MATIC");
                    double rq2 = requetteHttp.recupererSoldeCosmos("erowan");
                    double rq3 = requetteHttp.recupererSoldeCosmos("ATOM");
                    double rq4 = requetteHttp.recupererSoldeCosmos("USDT");
                    sql.modifierValeurMaticBD(rq1);
                    sql.modifierValeurErowanBD(rq2);
                    sql.modifierValeurAtomBD(rq3);
                    sql.modifierValeurUsdtBD(rq4);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    apr1 = executionWebScrapping.execution(2);
                    apr2 = executionWebScrapping.execution(1);
                    String APR1 = apr1;
                    String APR2 = apr2;
                    APR1 = APR1.replace("%", "");
                    APR2 = APR2.replace("%", "");
                    double bonAPR1 = Double.parseDouble(APR1);
                    double bonAPR2 = Double.parseDouble(APR2);
                    sql.ajouterValeurBD(bonAPR1, bonAPR2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread.sleep(300000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
