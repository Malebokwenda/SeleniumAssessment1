package Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ExtentReport {

    private void ExtentReport(){};

    public  static  void initReports(){
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Report");
        spark.config().setReportName("Extent Reports");

    }

    public  static void createTest(String testcasename){
        ExtentReports extent = new ExtentReports();
        ExtentTest test = extent.createTest(testcasename);

    }
    public static void flushReports() throws IOException {
        ExtentReports extent = new ExtentReports();
        extent.flush();
        Desktop.getDesktop().browse(new File("target/Spark.html").toURI());
    }

}


