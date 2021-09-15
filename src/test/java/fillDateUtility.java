import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class fillDateUtility {

    static ExtentTest test;
    public static  DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("dd:mm:yyyy");
    @Test
    public void fillDate(){

        try {
            test = reporter.extent.createTest("File date in Field");
            driverSetup driverSetup = new driverSetup();
            String path = new File(".").getCanonicalPath();
            File fis = new File(path + "/src/test/resources/SourceCSV.csv");
            ChromeDriver driver = driverSetup.launchBrowser("file:" + path + "/src/test/resources/HTML_sample.html", path);

            List<Map<String, String>> csvInMap = read(fis);
            driver.switchTo().frame("frm1");
            driver.switchTo().frame("frm2");
            String user = driver.findElement(By.id("usrName")).getText().trim();
            String date="";
            for (Map<String, String> valuesMap : csvInMap) {
                if (valuesMap.get("firstName").contains(user)) {
                    date = valuesMap.get("date");
                    reporter.reporterFunction(test, "INFO", "Record Picked up "+valuesMap,driver);
                    date=dateCalculator(date);
                    reporter.reporterFunction(test, "INFO", "Converted Date  "+date,driver);
                }
            }

            System.out.println("The final date is "+date);
            driver.findElement(By.id("expiryData")).sendKeys(date);
            reporter.reporterFunction(test, "PASS", "Updated date on the html form ",driver);
            reporter.extent.flush();
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, String>> read(File file) throws Exception {
        List<Map<String, String>> response = new LinkedList<Map<String, String>>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        MappingIterator<Map<String, String>> iterator = mapper.reader(Map.class)
                .with(schema)
                .readValues(file);
        while (iterator.hasNext()) {
            response.add(iterator.next());
        }
        return response;
    }

    public static String dateCalculator(String date) {

        String strDate="";
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date dateFormatted = formatter.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(dateFormatted);
            c.add(Calendar.YEAR, 3);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date finalDate = c.getTime();
            DateFormat dateFormat = new SimpleDateFormat("EEEE,dd MMMM yyyy");
            strDate = dateFormat.format(finalDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

}
