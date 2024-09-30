package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ModuleRedirectLink {

    public static void main(String[] args) {

        // Optional: set ChromeOptions if needed (e.g., headless mode)
         ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver();
 
        try {
            // Navigate to the cheatsheets page
            driver.get("https://addwebwpstg.addwebprojects.com/");

            // Find the section containing cheatsheets links
            WebElement cheatsheetsSection = driver.findElement(By.xpath("//*[@id=\"menu-header-mega-menu\"]"));
          //*[@id=\"menu-item-2823\"]/div
            // Find all <a> links within the cheatsheets section
            List<WebElement> links = cheatsheetsSection.findElements(By.tagName("a"));

            // Print each link's text and href attribute
            for (WebElement link : links) {
                String linkText = link.getText().trim();
                String url = link.getAttribute("href").trim();
                if (!url.isEmpty()) { // Print only if href attribute is not empty
                	System.out.println("");
                    System.out.println("Link Text: " + linkText + " URL: " + url);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
