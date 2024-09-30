package AddWebSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class SitemapRedirectChecker {

    public static void main(String[] args) {
        // Sitemap URL of the website
        String sitemapUrl = "https://www.voltactivedata.com/sitemap_index.xml"; // Replace with your sitemap URL

        SitemapRedirectChecker checker = new SitemapRedirectChecker();
        Set<String> urls = checker.fetchSitemapUrls(sitemapUrl);

        checker.checkRedirects(urls);
    }

    public Set<String> fetchSitemapUrls(String sitemapUrl) {
        Set<String> urls = new HashSet<>();
        try {
            System.out.println("Fetching URLs from sitemap: " + sitemapUrl);
            Document doc = Jsoup.connect(sitemapUrl).get();
            for (Element urlElement : doc.select("sitemap loc")) {
                String sitemapUrlEntry = urlElement.text();
                System.out.println("Fetching URLs from sitemap entry: " + sitemapUrlEntry);
                Document sitemapDoc = Jsoup.connect(sitemapUrlEntry).get();
                for (Element url : sitemapDoc.select("url loc")) {
                    String urlText = url.text();
                    System.out.println("Found URL in sitemap: " + urlText);
                    urls.add(urlText);
                }
            }
        } catch (IOException e) {
            System.out.println("Error fetching sitemap: " + e.getMessage());
        }
        return urls;
    }

    public void checkRedirects(Set<String> urls) {
        int redirectCount = 0;
        int totalUrls = urls.size();

        System.out.println("Total URLs to check: " + totalUrls);

        for (String url : urls) {
            if (isRedirect(url)) {
                redirectCount++;
            }
        }

        System.out.println("Total URLs: " + totalUrls);
        System.out.println("Redirect URLs: " + redirectCount);
    }

    private boolean isRedirect(String urlString) {
        try {
            System.out.println("Checking URL: " + urlString);
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

