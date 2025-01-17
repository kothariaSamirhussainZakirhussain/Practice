package AddWebSite;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class VerifyLinks {

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://www.exabeam.com/pages");

        List<WebElement> links = driver.findElements(By.tagName("a"));

        System.out.println("Total links are " + links.size());

        for (int i = 0; i < 1; i++) {
            WebElement ele = links.get(i);

            String url = ele.getAttribute("href");

            verifyLinkActive(url);
        }
    }

    public static void verifyLinkActive(String linkUrl) {
        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

            httpURLConnect.setConnectTimeout(3000);

            httpURLConnect.connect();

            if (httpURLConnect.getResponseCode() == 200) {
                System.out.println(
                        linkUrl + " - " + httpURLConnect.getResponseMessage()
                );
            }
            if (
                    httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND
            ) {
                System.out.println(
                        linkUrl +
                                " - " +
                                httpURLConnect.getResponseMessage() +
                                " - " +
                                HttpURLConnection.HTTP_NOT_FOUND
                );
            }
        } catch (Exception e) {}
    }
}


