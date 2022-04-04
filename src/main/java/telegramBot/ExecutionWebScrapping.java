package telegramBot;

public class ExecutionWebScrapping {
    public String execution(int choix) throws InterruptedException {
        String addresseWeb = "https://www.stakingrewards.com/earn/sifchain/";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ludov\\Downloads\\chromedriver.exe");

        WebScrapping webScrapping1 = new WebScrapping();

        if (choix == 1){
            return webScrapping1.aprReel(addresseWeb);
        } else {
            return webScrapping1.aprGeneric(addresseWeb);
        }
    }
}
