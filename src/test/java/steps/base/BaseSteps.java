package steps.base;

import com.google.inject.Inject;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.WebDriver;

public class BaseSteps {
    protected WebDriver driver;
    protected NgWebDriver ngWebDriver;

    @Inject
    public BaseSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        this.driver = driver;
        this.ngWebDriver = ngWebDriver;
    }
}
