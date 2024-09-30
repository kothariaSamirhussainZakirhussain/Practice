package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImportFile {
	public static void main(String[] args) throws InterruptedException {

		String Upload = "C:\\Users\\Addweb\\Downloads\\import_project_issues (1).xlsx";
		
		WebDriver driver = new ChromeDriver();

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
		driver.findElement(By.xpath("//*[@id=\"sideMenuScroll\"]/ul/li[5]")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("#sideMenuScroll > ul > li.accordionItem.openIt > div > a:nth-child(2)"))
				.click();
		
		Thread.sleep(2000);
		driver.findElement(By.id("search-text-field")).sendKeys("Testing05");
		
		Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"row-608\"]/td[3]/div/div/h5/a")).click();
      
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("#mob-client-detail > nav > ul > li:nth-child(8) > a > span")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id=\"fullscreen\"]/div[4]/div[1]/div[2]/div/div[1]/span/a")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id=\"fullscreen\"]/div[4]/div[1]/div[2]/div/div[1]/span/button")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.id("customFile")).sendKeys(Upload);
		
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

       

    
        // Close the browser
        driver.quit();
		
		
		
	}
}