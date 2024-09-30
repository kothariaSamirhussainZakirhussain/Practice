package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ExportFileValidation {
    public static void main(String[] args) {
      
        // Define the download path
        String downloadPath = "C:\\Users\\Addweb\\eclipse-workspace\\Selenium\\DownloadsFile"; // Replace with actual download directory

        // Set ChromeOptions to configure download directory
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        // Initialize WebDriver with ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        try {
            // Open the website
            driver.get("https://ttstage.addwebprojects.com/");
            driver.manage().window().maximize();

            // Perform login
            driver.findElement(By.id("email")).sendKeys("saurabhdhariwal.com@gmail.com");
            driver.findElement(By.cssSelector("input[name='password']")).sendKeys("addweb123");
            WebElement checkbox = driver.findElement(By.id("checkbox-signup"));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            driver.findElement(By.id("submit-login")).click();

            // Wait for login to complete (you can replace with WebDriverWait for better practice)
            Thread.sleep(2000);

            // Navigate to the download link
            driver.findElement(By.xpath("//*[@id=\"sideMenuScroll\"]/ul/li[5]")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#sideMenuScroll > ul > li.accordionItem.openIt > div > a:nth-child(5)")).click();
            Thread.sleep(2000);

            // Click on the download link to initiate download
            driver.findElement(By.xpath("//*[@id=\"fullscreen\"]/div[5]/div[1]/span/a")).click();

            // Wait for download to complete (adjust time based on your network speed and file size)
            Thread.sleep(5000);

            // Check if the file is downloaded
            boolean fileDownloaded = isFileDownloaded(downloadPath, "users_engagement.csv");

            // Print download status
            if (fileDownloaded) {
                System.out.println("File downloaded successfully.");
            } else {
                System.out.println("File not downloaded.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    // Method to check if file is downloaded
    private static boolean isFileDownloaded(String downloadPath, String fileName) {
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        if (dirContents != null) {
            for (File file : dirContents) {
                if (file.getName().equals(fileName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
