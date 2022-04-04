package telegramBot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebScrapping {
    WebDriver driver = new ChromeDriver();

    public String aprReel(String addWeb) throws InterruptedException {
        // termsAndConditions.click();
        driver.get(addWeb);

        WebElement termsAndConditions = driver.findElement(By.xpath("//*[@id=\"asset\"]/main/div/div[2]/section/div[3]/div[8]/span[2]"));
        String prixAPR = termsAndConditions.getText();
        Thread.sleep(100);

        driver.quit();

        return prixAPR;
    }

    public String aprGeneric(String addWeb) throws InterruptedException {
        driver.get(addWeb);

        WebElement termsAndConditions = driver.findElement(By.xpath("//*[@id=\"asset\"]/main/div/div[2]/section/div[3]/div[4]/span[2]"));
        String prixAPR = termsAndConditions.getText();
        Thread.sleep(100);

        driver.quit();

        return prixAPR;
    }
}
