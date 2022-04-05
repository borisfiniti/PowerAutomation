package pages.base;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import projectUtils.DriverProviderService;
import projectUtils.ProjectUtils;

public class BasePage {

    public final WebDriver driver;
    public NgWebDriver ngWebDriver;
    protected final ProjectUtils utils;

    public BasePage(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
        this.utils = new ProjectUtils();
    }

    public void waitForPageLoad() {
        ngWebDriver.waitForAngularRequestsToFinish();
    }

}
