package world;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.paulhammant.ngwebdriver.NgWebDriver;
import cucumber.runtime.java.guice.ScenarioScoped;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import projectUtils.DriverProviderService;

public class E2EModule extends AbstractModule {

    @Override
    protected void configure() {}

    @Provides
    @ScenarioScoped
    @Inject
    public synchronized WebDriver provideWebDriver() {
        //NgWebDriver needs to be set up here, just after the WebDriver otherwise it will open a SEPARATE driver (browser) upon instantiation
        WebDriver driver = DriverProviderService.getGoogleChromeDriver();
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        NgWebDriver ngWebDriver = new NgWebDriver(jsDriver);
        DriverProviderService.setJsDriver(jsDriver);
        DriverProviderService.setNgDriver(ngWebDriver);
        return driver;
    }

    @Provides
    @ScenarioScoped
    @Inject
    public synchronized NgWebDriver provideNgWebDriver() {
        return DriverProviderService.getNgDriver();
    }

}
