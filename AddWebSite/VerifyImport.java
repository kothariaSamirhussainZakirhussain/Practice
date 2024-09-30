package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class VerifyImport {
    public static void main(String[] args) throws InterruptedException, IOException {
        String uploadFilePath = "C:\\Users\\Addweb\\Downloads\\import_project_issues (1).xlsx";
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://ttstage.addwebprojects.com/");
            driver.manage().window().maximize();

            driver.findElement(By.id("email")).sendKeys("saurabhdhariwal.com@gmail.com");
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("input[name='password']")).sendKeys("addweb123");
            Thread.sleep(2000);
            WebElement checkbox = driver.findElement(By.id("checkbox-signup"));

            if (!checkbox.isSelected()) {
                checkbox.click();
            }

            driver.findElement(By.id("submit-login")).click();
            Thread.sleep(2000);

            // Navigate to the download link
            driver.findElement(By.xpath("//*[@id=\"sideMenuScroll\"]/ul/li[5]")).click();
            Thread.sleep(2000);
            driver.findElement(By.cssSelector("#sideMenuScroll > ul > li.accordionItem.openIt > div > a:nth-child(2)")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id=\"row-608\"]/td[3]/div")).click();

            driver.findElement(By.xpath("//*[@id=\"mob-client-detail\"]/nav/ul/li[8]/a/span")).click();
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id=\"fullscreen\"]/div[4]/div[1]/div[2]/div/div[1]/span/a")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"fullscreen\"]/div[4]/div[1]/div[2]/div/div[1]/span/button")).click();
            Thread.sleep(2000);

            // Upload the file
            driver.findElement(By.id("customFile")).sendKeys(uploadFilePath);
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"import-project-issues\"]/div/div/div[2]/form/button")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"table-actions\"]/div/button")).click();

            // Verify the file is uploaded
            Thread.sleep(2000);
            WebElement successMessage = driver.findElement(By.xpath("//*[@id=\"fullscreen\"]/div[4]/div[1]/div[2]/div/div[1]")); // Adjust the locator to match the success message element

            if (successMessage.isDisplayed()) {
                System.out.println("File uploaded successfully and the success message is displayed.");
            } else {
                System.out.println("File upload failed or success message not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
