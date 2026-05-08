import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

/**
 * PracticeSiteTest demonstrates basic Selenium WebDriver tests using TestNG.
 *
 * Each test is independent and follows the Arrange-Act-Assert (AAA) pattern.
 * Thread.sleep is used to slow down execution for demonstration purposes.
 */
public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Sets up a new browser instance before each test.
     */
    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();  // Download and manage the ChromeDriver
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        driver = new FirefoxDriver(options);  // Create a new instance of ChromeDriver
        driver.manage().window().maximize(); // Maximize the browser window
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Closes the browser after each test.
     * A short delay is added so the result is visible.
     */
    @AfterMethod
    public void tearDown() throws InterruptedException {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Verifies that the homepage title contains "The Internet".
     *
     * Steps:
     * 1. Open homepage
     * 2. Retrieve title
     * 3. Assert expected text is present
     */
    @Test
    public void verifyHomePageTitle() throws InterruptedException {

        // Arrange
        String url = "https://the-internet.herokuapp.com";

        // Act
        driver.get(url);

        String title = driver.getTitle();

        // Assert
        assertTrue(title.contains("The Internet"));
    }

    /**
     * Verifies navigation to the Checkboxes page.
     *
     * Steps:
     * 1. Open homepage
     * 2. Click "Checkboxes" link
     * 3. Verify URL contains "checkboxes"
     */
    @Test
    public void verifyNavigationToCheckboxPage() throws InterruptedException {

        // Arrange
        driver.get("https://the-internet.herokuapp.com");

        // Act
        driver.findElement(By.linkText("Checkboxes")).click();

        String url = driver.getCurrentUrl();

        // Assert
        assertTrue(url.contains("checkboxes"));
    }

    /**
     * Verifies that a checkbox can be selected.
     *
     * Steps:
     * 1. Open checkbox page
     * 2. Select checkbox if not already selected
     * 3. Verify checkbox is selected
     */
    @Test
    public void selectCheckbox() throws InterruptedException {

        // Arrange
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        // Act
        WebElement checkbox = driver.findElement(By.cssSelector("input[type='checkbox']"));

        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        // Assert
        assertTrue(checkbox.isSelected());
    }

    /**
     * Verifies that dynamically loaded content appears after clicking start.
     *
     * Steps:
     * 1. Open dynamic loading page
     * 2. Click start button
     * 3. Wait for "Hello World!" text
     * 4. Verify text is correct
     */
    @Test
    public void verifyDynamicContentLoads() throws InterruptedException {

        // Arrange
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        // Act
        driver.findElement(By.cssSelector("#start button")).click();

        WebElement finishText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='finish']/h4"))
        );

        // Assert
        assertEquals(finishText.getText(), "Hello World!");
    }

    @Test
    public void validLogin() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/login");

        // Act
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        WebElement successText = driver.findElement(By.xpath("//div[@class='example']/h2"));

        // Assert
        assertEquals(successText.getText(), "Secure Area");
        
    }

    @Test
    public void invalidLogin() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/login");

        // Act
        driver.findElement(By.id("username")).sendKeys("bad");
        driver.findElement(By.id("password")).sendKeys("credentials");
        driver.findElement(By.className("radius")).click();

        WebElement error = driver.findElement(By.id("flash"));

        // Assert
        assertTrue(error.getText().contains("Your username is invalid!"));
        
    }

    @Test
    public void logout() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/login");

        // Act
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();

        driver.findElement(By.xpath("//div[@class='example']/a")).click();

        // Assert
        assertTrue(driver.getCurrentUrl().contains("login"));
        
    }

    @Test
    public void clearFields() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/login");
        WebElement uname = driver.findElement(By.xpath("//div[@class='row']/div/input[@name='username']"));
        WebElement pass = driver.findElement(By.cssSelector("input#password"));

        // Act
        uname.sendKeys("tomsmith");
        pass.sendKeys("SuperSecretPassword!");
        
        uname.clear();
        pass.clear();

        uname.sendKeys("random");
        pass.sendKeys("input");


        // Assert
        assertTrue(uname.getDomProperty("value").contains("random"));
        assertTrue(pass.getDomProperty("value").contains("input"));
    }

    @Test
    public void partialText() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/");

        // Act
        driver.findElement(By.partialLinkText("Challenging")).click();
        
        // Assert
        assertTrue(driver.getCurrentUrl().contains("challenging_dom"));
    }

    @Test
    public void dropdown() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement element = driver.findElement(By.id("dropdown"));
        Select dropdown = new Select(element);

        //Act
        dropdown.selectByVisibleText("Option 1");
        dropdown.selectByVisibleText("Option 2");

        //Assert
        assertTrue(element.getDomProperty("value").contains("2"));
    }

    @Test
    public void checkboxesAgain() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        List<WebElement> checkboxs = driver.findElements(By.cssSelector("input[type='checkbox']"));

        // Act
        for (WebElement checkbox : checkboxs) {
            if (checkbox.getDomProperty("checked").equals("false")) {
                checkbox.click();
            }
        }

        // Assert
        for (WebElement checkbox : checkboxs) {
            assertTrue(checkbox.getDomProperty("checked").equals("true"));
        }
    }

    @Test
    public void alert() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        List<WebElement> buttons = driver.findElements(By.xpath("//ul/li/button"));

        // Act
        buttons.getFirst().click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // Assert
        assertTrue(driver.findElement(By.id("result")).getText().contains("You successfully clicked an alert"));
    }

    @Test
    public void secondAlert() {
        // Arrange
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        List<WebElement> buttons = driver.findElements(By.xpath("//ul/li/button"));

        // Act
        buttons.get(1).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.dismiss();

        assertTrue(driver.findElement(By.id("result")).getText().contains("You clicked: Cancel"));
    }
}