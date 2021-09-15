import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

public class reporter {
   public  static ExtentReports extent = new ExtentReports();
  public static ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
    public  static void reporterFunction(ExtentTest test, String status, String message, ChromeDriver driver) {

        try {
            extent.attachReporter(spark);
            switch (status) {
                case "PASS": {
                    test.pass(message);
                    String screenShotPath = capture(driver, "testImage");
                    test.pass(MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
                    break;
                }
                case "INFO":
                    test.info(message);
                    break;

                case "FAIL":
                   // Assert.assertTrue("fail",1==0);
                    test.fail(message);
                    break;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }
    public static String capture(WebDriver driver, String screenShotName) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"/Screenshots/"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);

        return dest;
    }



}
