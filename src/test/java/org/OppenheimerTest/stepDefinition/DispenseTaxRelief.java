package org.OppenheimerTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.OppenheimerTest.roles.Clerk;
import org.OppenheimerTest.roles.Employee;
import org.OppenheimerTest.roles.Governor;
import org.OppenheimerTest.utility.CommonFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class DispenseTaxRelief {

    WebDriverWait wait;
    WebDriver driver;
    Response respDispense;

    @Given("the governor has accessed to portal")
    public void the_governor_has_accessed_to_portal() {

        System.setProperty("webdriver.chrome.driver", Config.driverDir);
        driver = new ChromeDriver();
        driver.get(Config.baseUri);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }

    @Given("the governor see there are {int} working class hero records")
    public void the_governor_see_there_are_working_class_hero_records(int numOfRecords) {

        List<Employee> employeeList = CommonFunction.generateEmployeeList(numOfRecords);
        Clerk.insertMultiple(employeeList);
    }

    @When("the governor click on {string} button")
    public void the_governor_click_on_button(String buttonLabel) {

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#contents > a.btn.btn-danger.btn-block")));
        WebElement dispenseButton = driver.findElement(By.cssSelector("#contents > a.btn.btn-danger.btn-block"));
        Assert.assertEquals(dispenseButton.getText(), buttonLabel);
        Assert.assertEquals(Color.fromString(dispenseButton.getCssValue("background-color")).asHex(), "#dc3545");
        dispenseButton.click();

    }

    @Then("dispense page is displayed")
    public void dispense_page_is_displayed() {

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='display-4 font-weight-bold']")));
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8080/dispense");

        WebElement dispenseMessage = driver.findElement(By.xpath("//div[@class='display-4 font-weight-bold']"));
        Assert.assertEquals(dispenseMessage.getText(), "Cash dispensed");
        driver.quit();
    }

    @When("the governor dispense tax relief via API")
    public void the_governor_dispense_tax_relief_via_api() {
        respDispense = Governor.dispense();
        respDispense.then().assertThat().statusCode(200);
    }

    @Then("tax relief are successfully dispensed")
    public void tax_relief_are_successfully_dispensed() {

        Assert.assertEquals(respDispense.htmlPath().getString("html.body.div"), "Cash dispensed");
    }
}
