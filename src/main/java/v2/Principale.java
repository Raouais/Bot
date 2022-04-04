package v2;

public class Principale {
    public static void main(String[] args) {
        PoolInvestissement poolInvestissement = new PoolInvestissement();
        poolInvestissement.demarrePersonne();
        CustomThread customThread = new CustomThread();
        customThread.start();
    }
}
