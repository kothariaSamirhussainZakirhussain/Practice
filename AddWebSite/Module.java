package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Module {

    private static Map<Integer, Integer> responseCodeCounts = new HashMap<>();

    public static void main(String[] args) {

        // Optional: set ChromeOptions if needed (e.g., headless mode)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to the main page
            driver.get("https://addwebwpstg.addwebprojects.com/");

            // List of XPaths for different modules
            String[] moduleXpath = {
                "//*[@id=\"menu-header-mega-menu\"]/li[1]",
                "//*[@id=\"menu-header-mega-menu\"]/li[2]",
                "//*[@id=\"menu-header-mega-menu\"]/li[3]",
                "//*[@id=\"menu-header-mega-menu\"]/li[4]",
                "//*[@id=\"menu-header-mega-menu\"]/li[5]",
                "//*[@id=\"menu-header-mega-menu\"]/li[6]"
            };

            Set<String> printedLinks = new HashSet<>();

            // Loop through each module
            for (String moduleXpaths : moduleXpath) {
                WebElement moduleSection = driver.findElement(By.xpath(moduleXpaths));
                List<WebElement> moduleLinks = moduleSection.findElements(By.tagName("a"));

                System.out.println("\nModule: " + moduleSection.getText().split("\n")[0]); // Print module name

                // Print each link's text, href attribute, and response code within the module
                for (WebElement link : moduleLinks) {
                    String linkText = link.getText().trim();
                    String url = link.getAttribute("href").trim();
                    if (!url.isEmpty() && !printedLinks.contains(url)) { // Print only if href attribute is not empty and not already printed
                        printedLinks.add(url);
                        int responseCode = getResponseCode(url);
                        System.out.println("  Link Text: " + linkText + " URL: " + url + ", Response Code: " + responseCode);
                    }
                }
            }

            // Print the count for each response code
            System.out.println("\nResponse Code Counts:");
            for (Map.Entry<Integer, Integer> entry : responseCodeCounts.entrySet()) {
                System.out.println("Response Code: " + entry.getKey() + " - Count: " + entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    private static int getResponseCode(String urlString) {
        try {
            URL url = new URL(urlString);
            if (!url.getProtocol().startsWith("http")) {
                System.out.println("Skipping non-HTTP URL: " + urlString);
                return -1;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            responseCodeCounts.put(responseCode, responseCodeCounts.getOrDefault(responseCode, 0) + 1);
            return responseCode;

        } catch (IOException e) {
            System.out.println("Error checking URL: " + urlString + " - " + e.getMessage());
            responseCodeCounts.put(-1, responseCodeCounts.getOrDefault(-1, 0) + 1); // -1 for errors
            return -1;
        }
    }
}
