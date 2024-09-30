package AddWebSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MethodDemo {
    
    @FindBy(how = How.ID, using = "submit-login")
    public WebElement submitLogin;

    @FindBy (how = How.ID, using = "email")
    public WebElement email;
    
    @FindBy (how = How.CSS, using = "input[name='password']")
    public WebElement pass;
    
    // Constructor to initialize the web elements
    public MethodDemo(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Method to perform login
    public void Leads(WebDriver driver, String user, String password) {
    	
        driver.get("https://ttstage.addwebprojects.com/");
        driver.manage().window().maximize();

//        Thread.sleep(2000);
//        driver.findElement(By.id("email")).sendKeys(user);
//
//        Thread.sleep(2000);
//        driver.findElement(By.cssSelector("input[name='password']")).sendKeys(pass);

        WebElement checkbox = driver.findElement(By.id("checkbox-signup"));

        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        email.sendKeys(user);
        pass.sendKeys(password);
        submitLogin.click();
    }

    public static void main(String[] args) throws InterruptedException {
       
        WebDriver driver = new ChromeDriver();

        // Create an instance of the MethodDemo class
        MethodDemo methodDemo = new MethodDemo(driver);

        // Perform login
        methodDemo.Leads(driver, "saurabhdhariwal.com@gmail.com", "addweb123");

        // Add any additional actions here

        Thread.sleep(10000);
        driver.quit();
    }
}
