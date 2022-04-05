package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import projectUtils.CreateJVMReportFromJson;

import java.io.File;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 */
//@CucumberOptions(plugin = {"pretty",  "html:target/cucumber-html-report"},
@CucumberOptions(plugin = {
        "rerun:target/rerun.txt",
        "html:target/cucumber-Html-Report",     //  for html result
        "json:target/cucumber-JSON-report/cucumber.json"   // for json result
        },
        features = {"src/test/resources/appFeatures/test.feature"},
        glue = {"steps", "world"},
        tags={"@test_feature"})
public class RunTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)


    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterSuite(alwaysRun=true)
    public static void generateReport() throws IOException, ParseException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmm");
        File reportOutputDirectory = new File("target/cucumber-JVM-reports/" + dtf.format(currentDateTime));
        CreateJVMReportFromJson.generateJVMReport(reportOutputDirectory);
    }
}

