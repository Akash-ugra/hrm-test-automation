package io.akasht.hrm.automation.com;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for HRM Application.
 * Uses JUnit 5 for testing and Selenium WebDriver for browser automation.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HRMAppTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    /**
     * Sets up the WebDriver and WebDriverWait before all tests.
     */
    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions().addArguments(List.of("--no-sandbox", "--disable-dev-shm-usage"));
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Tests the home page title.
     */
    @Order(1)
    @Test
    public void testHomePageTitle() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        String title = driver.getTitle();
        WebElement pageHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[2]/h5")));
        assertEquals("OrangeHRM", title);
        assertEquals("Login", pageHeading.getText());
    }

    /**
     * Tests the login functionality.
     */
    @Order(2)
    @Test
    public void testLogin() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginWithCreds();
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[1]/header/div[1]/div[1]/span/h6")));
        WebElement nameElement = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/p"));
        assertEquals("Dashboard", titleElement.getText());
        assertEquals("Akash Thakur", nameElement.getText());
    }

    /**
     * Tests the information page.
     */
    @Order(3)
    @Test
    public void testInfo() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        if (!isLoggedIn()) {
            loginWithCreds();
        }
        WebElement infoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[1]/aside/nav/div[2]/ul/li[3]/a")));
        infoElement.click();
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[1]/div/div/div/div[2]/div[1]/div[2]/input")));
        WebElement lastNameElement = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[1]/div/div/div/div[2]/div[3]/div[2]/input"));
        WebElement empIdElement = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[2]/div[1]/div[1]/div/div[2]/input"));
        assertEquals("Akash", nameElement.getAttribute("value"));
        assertEquals("Thakur", lastNameElement.getAttribute("value"));
        assertEquals("0500", empIdElement.getAttribute("value"));
    }

    /**
     * Tests the claim submission functionality.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Order(4)
    @Test
    public void testClaimSubmit() throws InterruptedException {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        if (!isLoggedIn()) {
            loginWithCreds();
        }
        WebElement claimElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[1]/aside/nav/div[2]/ul/li[7]/a")));
        claimElement.click();
        WebElement submitClaimElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[1]/header/div[2]/nav/ul/li[1]/a")));
        submitClaimElement.click();
        WebElement currencyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[2]/div/div[2]/div/div/div[2]/i")));
        currencyElement.click();
        new Actions(driver).sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER)).perform();
        WebElement eventElement = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[1]/div/div[1]/div/div[2]/div/div/div[1]"));
        eventElement.click();
        new Actions(driver).sendKeys(Keys.chord(Keys.ARROW_DOWN, Keys.ENTER)).perform();
        WebElement createElement = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[3]/button[2]"));
        createElement.click();
        WebElement eventNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/div[1]/form/div[1]/div/div[2]/div/div[2]/input")));
        assertEquals("Accommodation", eventNameElement.getAttribute("value"));
        WebElement statusElement = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/div[1]/form/div[1]/div/div[3]/div/div[2]/input"));
        assertEquals("Initiated", statusElement.getAttribute("value"));
        driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/div[9]/button[3]")).click();
        Thread.sleep(2000);
        Boolean isPresent = wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/div[1]/form/div[1]/div/div[3]/div/div[2]/input"), "Submitted"));
        assertEquals(true, isPresent);
    }

    /**
     * Checks if the user is logged in.
     * @return true if the user is logged in, false otherwise
     */
    private Boolean isLoggedIn() {
        try {
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/p")));
            return nameElement.getText().equals("Akash Thakur");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Logs in with predefined credentials.
     */
    private void loginWithCreds() {
        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input")));
        usernameElement.sendKeys("Akash");
        WebElement passwordElement = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input"));
        passwordElement.sendKeys("Password123");
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button")).click();
    }

    /**
     * Tears down the WebDriver after all tests.
     */
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
