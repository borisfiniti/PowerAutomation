package world;

import com.google.inject.Inject;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.AfterStep;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriverException;
import projectUtils.DriverProviderService;
import projectUtils.Sleeper;
import projectUtils.processors.ApplicationToTest;
import projectUtils.processors.PropertiesProcessor;
import projectUtils.processors.RunProcessor;

import java.io.IOException;

public class Hooks {
    private final WebDriver driver;

        @Inject
        public Hooks(WebDriver driver) {
            this.driver = driver;
        }

        @Before
        public void beforeClassSetup() {
            openLogInPage();
        }

        private void openLogInPage() {
            driver.get(RunProcessor.getTestUrl());
        }

        @After
        public void afterClass() {
            driver.close();
        }

    @AfterStep
    public void afterStepHook(Scenario scenario) throws IOException {
        try {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        } catch (WebDriverException e) {
            System.out.println(">>>>>WARNING: SCREENSHOT FAILED!");
        }
    }


}
