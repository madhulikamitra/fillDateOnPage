import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class driverSetup {


    public ChromeDriver launchBrowser(String url,String path){


            if(System.getProperty("os.name").contains("windows"))
            System.setProperty("webdriver.chrome.driver", path+"/src/test/resources/drivers/chromedriver.exe");
            else
                System.setProperty("webdriver.chrome.driver", path+"/src/test/resources/drivers/chromedriverMac");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            ChromeDriver driver = new ChromeDriver(options);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
return driver;

    }
}
