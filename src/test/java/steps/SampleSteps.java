package steps;

import com.google.inject.Inject;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages.appPages.SamplePage;
import steps.base.BaseSteps;

public class SampleSteps extends BaseSteps {
    private SamplePage samplePage;
    private static int operandOne;
    private static int operandTwo;
    private static int result;

    @Inject
    public SampleSteps(WebDriver driver, NgWebDriver ngWebDriver) {
        super(driver, ngWebDriver);
        this.samplePage = PageFactory.initElements(driver, SamplePage.class);
    }

    @Given("I multiply {int} by {int}")
    public void iDoTheSampleSteps(int a, int b) {
        operandOne = a;
        operandTwo = b;
        samplePage
                .multiply(operandOne, operandTwo);
    }

    @Then("I collect multiplication results")
    public void iVerifySampleStepResults() {
        result = samplePage
                .getResult();
    }

    @Then("I verify that multiplication result is correct")
    public void iVerifyThatMultiplicationResultIsCorrect() {
        Assert.assertTrue(result == operandOne * operandTwo, "TEST FAILED: Multiplication result is INCORRECT!");
    }
}
