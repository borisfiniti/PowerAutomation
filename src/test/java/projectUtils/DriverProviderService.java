package projectUtils;

import com.paulhammant.ngwebdriver.NgWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import projectUtils.processors.PropertiesProcessor;
import projectUtils.processors.PropertyTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class DriverProviderService {

    private static WebDriver gcDriver;
    private static NgWebDriver ngDriver;
    private static JavascriptExecutor jsDriver;

    public static WebDriver getGoogleChromeDriver() {
        if (gcDriver!=null)
            return gcDriver;
        else {
            gcDriver = provideGoogleChromeDriver();
            return getGoogleChromeDriver();
        }
    }

    public static NgWebDriver getNgDriver() {
        return ngDriver;
    }

    public static void setNgDriver(NgWebDriver ngWebDriver) {
        ngDriver = ngWebDriver;
    }

    public static JavascriptExecutor getJsDriver() {
        return jsDriver;
    }

    public static void setJsDriver(JavascriptExecutor javaScriptDriver) {
        jsDriver = javaScriptDriver;
    }

    //SetUp downloads
//    private static String downloadFolderName = "downloadedTestFiles" + ProjectUtils.getCurrentTimestamp();
//    private static String downloadFilepath = new File("").getAbsolutePath() + "\\target\\" + downloadFolderName;


    private static WebDriver provideGoogleChromeDriver() {

//        FileHandler.setCurrentDownloadFolderName(downloadFolderName);

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
//        chromePrefs.put("download.default_directory", downloadFilepath);
        chromePrefs.put("safebrowsing.enabled", "true");
        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setExperimentalOption("prefs", chromePrefs);
        chromeOpts.addArguments("--safebrowsing-disable-download-protection");
        chromeOpts.addArguments("--start-maximized");

        //Browser logs
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        chromeOpts.setCapability("goog:loggingPrefs", logPrefs);

        //Multimedia:
        chromeOpts.addArguments("use-fake-device-for-media-stream");
        chromeOpts.addArguments("use-fake-ui-for-media-stream");
        chromeOpts.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        prefs.put("profile.default_content_setting_values.notifications", 1);
//        prefs.put("download.default_directory", downloadFilepath);
        chromeOpts.setExperimentalOption("prefs", prefs);

        if (PropertiesProcessor.getPropertyByKey(PropertyTypes.RUN_PROPS, "run.in.headless").equals("true"))
            chromeOpts.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");

        //Automatically get Chromedriver binary file
        WebDriverManager.chromedriver().setup();

        //Return Chrome driver
        return new ChromeDriver(chromeOpts);

    }

    private static JavascriptExecutor provideJsDriver() {
        return (JavascriptExecutor)provideGoogleChromeDriver();
    }

    private static NgWebDriver provideNgWebDriver() {
        return new NgWebDriver(getJsDriver());
    }
}
