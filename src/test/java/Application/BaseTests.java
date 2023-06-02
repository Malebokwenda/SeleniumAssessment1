package Application;

import Reporter.ExtentReport;
import Utilities.Utilities;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.util.HashMap;

public class BaseTests {
    private WebDriver driver;
    Utilities excelData;

    @BeforeSuite
    public void beforeSuite() {
        ExtentReport.initReports();
    }

    @AfterSuite
    public void afterSuite() throws IOException {
        ExtentReport.flushReports();
    }


//    @Parameters({"browser", "browserUrl"})
//    @BeforeClass
//    public void setUp() {
//        setUp("chrome", "https://shop.demoqa.com/shop/");
//    }

    @Parameters({"browser", "browserUrl"})
    @BeforeClass
    public void setUp(@Optional String browser, String browserUrl) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            // Add any specific options for the Edge driver if needed
             driver = new EdgeDriver(options);
            WebDriverManager.chromedriver().setup();
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browser);
        }

        driver.get("https://shop.demoqa.com/shop/");
        WebElement dismissLink = driver.findElement(By.linkText("Dismiss"));
        dismissLink.click();

        excelData = new Utilities();
        excelData.setupExcel();
    }

    @Test(priority = 1 )
    public void InvalidLogin() throws InterruptedException {
        ExtentReport.createTest("InvalidloginTest");
        driver.findElement(By.xpath("//*[@id='noo-site']/header/div[1]/div/ul[2]/li[2]/a")).click();
        driver.findElement(By.id("username")).sendKeys("name");
        driver.findElement(By.id("password")).sendKeys("mmmm");
        driver.findElement(By.name("login")).click();
        System.out.println("ERROR: The username or password you entered is incorrect. Lost your password?");
        Thread.sleep(2500);

//        login = new LogIn(driver);
//        username = excelData.getCellData(1,1);
//        password = excelData.getCellData(1,2);
//        login.goTo();
//        login.invalidLogin(username,password);
    }

    @Test(priority = 2)
    public void validLogin() throws InterruptedException {
        ExtentReport.createTest("ValidloginTest");
        driver.findElement(By.xpath("//*[@id='noo-site']/header/div[1]/div/ul[2]/li[2]/a")).click();
        driver.findElement(By.id("username")).sendKeys("malebo");
        driver.findElement(By.id("password")).sendKeys("malebo@123");
        driver.findElement(By.name("login")).click();
        Thread.sleep(2500);
        driver.get("https://shop.demoqa.com/shop/");

        // Find item to add to cart
        WebElement item = driver.findElement(By.xpath("(//img[@class='product-one-thumb'])[1]"));
        item.click();

        // Select color from dropdown
        WebElement colorDropdown = driver.findElement(By.id("pa_color"));
        Select colorSelect = new Select(colorDropdown);
        colorSelect.selectByVisibleText("Beige");

        // Select size from dropdown
        WebElement sizeDropdown = driver.findElement(By.id("pa_size"));
        Select sizeSelect = new Select(sizeDropdown);
        sizeSelect.selectByVisibleText("Large");


        // Click on the "Add to cart" button
        WebElement addToCartButton = driver.findElement(By.xpath("(//button[normalize-space()='Add to cart'])[1]"));
        addToCartButton.click();

        driver.findElement(By.xpath("//*[@id='noo-site']/header/div[1]/div/ul[2]/li[2]/a")).click();

        driver.findElement(By.linkText("Logout")).click();
        Thread.sleep(2500);
    }

    @Test(priority = 3)
    public void checkout() throws InterruptedException {
        ExtentReport.createTest("CheckoutTest");
        driver.findElement(By.xpath("//*[@id=\"noo-site\"]/header/div[1]/div/ul[2]/li[2]/a")).click();
        driver.findElement(By.id("username")).sendKeys("malebo");
        driver.findElement(By.id("password")).sendKeys("malebo@123");
        driver.findElement(By.name("login")).click();
        Thread.sleep(2500);
        driver.get("https://shop.demoqa.com/shop/");

//        Find and click on the link to navigate to page 3
        WebElement page3Link = driver.findElement(By.linkText("3"));
        page3Link.click();

//            Find item to add to cart
        WebElement item = driver.findElement(By.xpath(" (//img[@class='product-one-thumb'])[1]"));
        item.click();

//            Select color from dropdown
        WebElement  colorDropdown = driver.findElement(By.id("pa_color"));
        Select colorSelect = new Select(colorDropdown);
        colorSelect.selectByVisibleText("Orange");

//            Select size from dropdown
        WebElement sizeDropdown = driver.findElement(By.id("pa_size"));
        Select sizeSelect = new Select(sizeDropdown);
        sizeSelect.selectByVisibleText("Large");

//            Click on the "Add to cart" button
        WebElement addToCartButton = driver.findElement(By.xpath("(//button[normalize-space()='Add to cart'])[1]"));
        addToCartButton.click();

//       click cart
//        WebElement cartButton = driver.findElement(By.xpath(" (//img[@class='product-one-thumb'])[1]"));
//        cartButton.click();

//        Proceed to the checkout
//
        WebElement checkoutButton = driver.findElement(By.linkText("Checkout"));
        checkoutButton.click();

//        Fill in personal info
        WebElement firstNameInput = driver.findElement(By.id("billing_first_name"));
        firstNameInput.sendKeys("Malebo");

        WebElement lastNameInput = driver.findElement(By.id("billing_last_name"));
        lastNameInput.sendKeys("Faith");


        WebElement companyInput = driver.findElement(By.id("billing_company"));
        companyInput.sendKeys("Digilink");


//         Select country
        WebElement  countryDropdown = driver.findElement(By.xpath("/html/body/div[1]/div[2]/main/article/div/div/form[2]/div/div[1]/div[1]/div/p[4]/span/select"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("South Africa");

//        address
        WebElement addressInput = driver.findElement(By.id("billing_address_1"));
        addressInput.sendKeys("123 Main St");

        WebElement cityInput = driver.findElement(By.id("billing_city"));
        cityInput.sendKeys("Cape Town");


        WebElement  provinceDropdown = driver.findElement(By.id("billing_state"));
        Select provinceSelect = new Select(provinceDropdown);
        provinceSelect.selectByVisibleText("Western Cape");

        WebElement zipInput = driver.findElement(By.id("billing_postcode"));
        zipInput.sendKeys("7441");

        WebElement cellNumberInput = driver.findElement(By.id("billing_phone"));
        cellNumberInput.sendKeys("0817625482");

        WebElement emailInput = driver.findElement(By.id("billing_email"));
        emailInput.sendKeys("malebofa.ith@gmail.com");

//        Click the terms and conditions checkbox
        WebElement termsCheckbox = driver.findElement(By.id("terms"));
        termsCheckbox.click();
        Thread.sleep(2500);

//      Click on the 'Place  Order' button
        WebElement completeOrderButton = driver.findElement(By.id("place_order"));
        completeOrderButton.click();

//        // Close the browser
//        driver.quit();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    }
