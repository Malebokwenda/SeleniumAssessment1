package Reporter;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class AttachScreenshots {
    WebDriver driver;
    com.aventstack.extentreports.ExtentReports extent = new com.aventstack.extentreports.ExtentReports();
    @BeforeSuite
    public void seUp() throws IOException {

        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Extent Reports");
        extent.attachReporter(spark);
    }
    @AfterSuite
    public void tearDown() throws IOException {
        extent.flush();
        Desktop.getDesktop().browse(new File("").toURI());

    }
    @Test
    public  void attachScreenShotsTest() throws IOException {
        ExtentTest test = extent.createTest("Dismiss Button");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        test.pass("Browser opened");
        driver.get("https://shop.demoqa.com/shop/");
        WebElement dismisslink = driver.findElement(By.linkText("Dismiss"));
        dismisslink.click();

        test.pass("Value entered", MediaEntityBuilder.createScreenCaptureFromPath(getScreenshotAsBase64()).build());

        test.pass("test finished");
    }

    public String getScreenshotAsBase64() throws IOException {
        File source = ( (TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")+"/Screenshots/image.png";
        FileUtils.copyFile(source,new File(path));
        byte[] imageBytes = IOUtils.toByteArray(new FileInputStream(path));
        return Base64.getEncoder().encodeToString(imageBytes) ;
    }
    public String getBase64(){
        return((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    }
}
