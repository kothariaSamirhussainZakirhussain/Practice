package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class HoverEffectLink {

    private static Set<String> visitedLinks = new HashSet<>();
    private static Queue<String> linksToVisit = new LinkedList<>();
    private static final String BASE_URL = "https://www.voltactivedata.com/sitemap_index.xml";

    public static void main(String[] args) {

        // Optional: set ChromeOptions if needed (e.g., headless mode)
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            // Start crawling from the base URL
            linksToVisit.add(BASE_URL);

            while (!linksToVisit.isEmpty()) {
                String currentUrl = linksToVisit.poll();
                if (!visitedLinks.contains(currentUrl) && currentUrl.startsWith(BASE_URL)) {
                    visitedLinks.add(currentUrl);
                    System.out.println("Checking URL: " + currentUrl);
                    checkRedirection(driver, currentUrl);
                    crawlPage(currentUrl);
                }
            }
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    public static void crawlPage(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            // Find all links on the page
            for (Element link : doc.select("a[href]")) {
                String absUrl = link.absUrl("href");
                // Add new links to the queue
                if (!visitedLinks.contains(absUrl)) {
                    linksToVisit.add(absUrl);
                }
            }
        } catch (IOException e) {
            System.out.println("Error crawling URL: " + e.getMessage());
        }
    }

    public static void checkRedirection(WebDriver driver, String urlString) {
        try {
            driver.get(urlString);
            Thread.sleep(2000); // Wait for the page to load

            // Find all links on the page
            List<WebElement> links = driver.findElements(By.tagName("a"));
            Actions actions = new Actions(driver);

            for (WebElement link : links) {
                String href = link.getAttribute("href");
                if (href != null && !href.isEmpty()) {
                    // Hover over the link to trigger any hover effects
                    actions.moveToElement(link).perform();
                    Thread.sleep(500); // Wait for any dynamic content to load

                    // Check if the link redirects
                    if (isRedirect(href)) {
                        System.out.println("Redirect link works: " + href);
                    } else {
                        System.out.println("Link does not redirect: " + href);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking link: " + e.getMessage());
        }
    }

    private static boolean isRedirect(String urlString) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setInstanceFollowRedirects(false); // Set to false to manually handle redirects

            int responseCode = connection.getResponseCode();
            // Check if the response code indicates a redirect (301, 302, 303)
            if (responseCode == HttpURLConnection.HTTP_MOVED_PERM ||
                responseCode == HttpURLConnection.HTTP_MOVED_TEMP ||
                responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                
                // Get the location header to find the new URL
                String newUrl = connection.getHeaderField("Location");
                System.out.println("Redirect: " + urlString + " -> " + newUrl);
                return true;
            } else if (responseCode == HttpURLConnection.HTTP_OK) {
                // The URL does not redirect but is valid
                System.out.println("Valid URL: " + urlString);
                return false;
            } else {
                // The URL does not work correctly
                System.out.println("Invalid URL: " + urlString + " (Response code: " + responseCode + ")");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Exception occurred while checking the URL: " + e.getMessage());
            return false;
        }
    }
}

