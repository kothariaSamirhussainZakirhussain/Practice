package AddWebSite;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllLinks{

    private static Set<String> uniqueLinks = new HashSet<>();
    private static int totalLinks = 0;

    public static void main(String[] args) {
        
        // Optional: set ChromeOptions if needed (e.g., headless mode)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        String baseUrl = "https://www.globalsqa.com/";
        driver.get(baseUrl);

        // Find all elements with 'a', 'link', 'img' tags and any element with 'href' or 'src' attribute
        List<WebElement> elements = driver.findElements(By.xpath("//a[@href] | //link[@href] | //img[@src]"));

        for (WebElement element : elements) {
            String url = element.getAttribute("href");
            if (url == null || url.isEmpty()) {
                url = element.getAttribute("src");
            }
            String tagName = element.getTagName();
            if (url != null && !url.isEmpty()) {
                totalLinks++;
                if (uniqueLinks.add(url)) { // Only unique URLs will be added
                    String altText = element.getAttribute("alt");
                    System.out.println("Unique URL found (Tag: " + tagName + ", Alt: " + altText + "): " + url);
                    checkRedirection(url);
                } else {
                    System.out.println("Duplicate URL found (Tag: " + tagName + "): " + url);
                }
            }
        }

        // Print total number of links and unique links found
        System.out.println("Total number of links found: " + totalLinks);
        System.out.println("Total number of unique links found: " + uniqueLinks.size());

        driver.quit();
    }

    public static void checkRedirection(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            String location = httpURLConnection.getHeaderField("Location");

            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
                responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
                responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                System.out.println("Redirected to: " + location);
                // Uncomment the line below if you want to follow the redirect chain
                // checkRedirection(location);
            } else {
                System.out.println("Final URL: " + urlString);
            }
        } catch (IOException e) {
            System.out.println("Error checking URL: " + e.getMessage());
        }
    }
}
