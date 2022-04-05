package pages.appPages;

import com.paulhammant.ngwebdriver.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pages.base.BasePage;
import projectUtils.DriverProviderService;
import projectUtils.ProjectUtils;

public class SamplePage extends BasePage {


    //Same thing, with Page Factory
    @ByAngularModel.FindBy(model = "first")
    private WebElement firstField;

    @ByAngularModel.FindBy(model = "second")
    private WebElement secondField;

    @ByAngularModel.FindBy(model = "operator")
    private WebElement operatorDropdown;

    @ByAngularButtonText.FindBy(buttonText = "Go!")
    public WebElement goButton;

    @FindBy(xpath = "//h2[@class='ng-binding']")
    private WebElement resultLabel;

    public SamplePage(WebDriver driver) {
        super(driver, DriverProviderService.getNgDriver());
    }

    public SamplePage populateFirstField(String input) {
        firstField.clear();
        firstField.sendKeys(input);
        return this;
    }

    public SamplePage populateSecondField(String input) {
        secondField.clear();
        secondField.sendKeys(input);
        return this;
    }

    public SamplePage populateOperationDropdown(String input) {
        new Select(operatorDropdown).selectByVisibleText(input);
        return this;
    }

    public SamplePage clickGoButton() {
        goButton.click();
        return this;
    }

    public SamplePage multiply(int  a, int b) {
        populateFirstField(Integer.toString(a))
            .populateSecondField(Integer.toString(b))
            .populateOperationDropdown("*");
        clickGoButton();
        return this;
    }

    public int getResult() {
        waitForPageLoad();
        return Integer.parseInt(resultLabel.getText().trim());
    }
}
